package com.amalitech.securesail.amalisecuresail.user_management.services;

import com.amalitech.securesail.amalisecuresail.global.dtos.response.DefaultResponse;
import com.amalitech.securesail.amalisecuresail.global.exceptions.EmailFailedException;
import com.amalitech.securesail.amalisecuresail.global.exceptions.OpsFailedException;
import com.amalitech.securesail.amalisecuresail.global.exceptions.UploadFailedException;
import com.amalitech.securesail.amalisecuresail.global.services.CloudinaryService;
import com.amalitech.securesail.amalisecuresail.global.services.EmailService;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.UserOpsFailedException;
import com.amalitech.securesail.amalisecuresail.user_management.models.Role;
import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import com.amalitech.securesail.amalisecuresail.global.constants.OperationResultConstants;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.OTPRequest;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.SignupRequest;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.response.SignUpResponse;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityAlreadyExistsException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityNotFoundException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.OTPExpiredException;
import com.amalitech.securesail.amalisecuresail.user_management.repositories.RoleRepository;
import com.amalitech.securesail.amalisecuresail.user_management.repositories.UserRepository;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final OTPService otpService;
    private final EmailService emailService;

    public ResponseEntity<SignUpResponse> register(SignupRequest request) {
        if (userRepository.existsByEmployeeEmail(request.employeeEmail())) {
            throw new EntityAlreadyExistsException(OperationResultConstants.USER_ALREADY_EXISTS.name());
        }
        if (userRepository.existsByPersonalEmail(request.personalEmail())) {
            throw new EntityAlreadyExistsException(OperationResultConstants.USER_ALREADY_EXISTS.name());
        }
        if (!request.employeeEmail().contains("@amalitech.com")) {
            throw new IllegalArgumentException("Email must be an AmaliTech email");
        }

        Role userRole = roleRepository.findByNameIgnoreCase(request.role()).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        String encodedPassword = passwordEncoder.encode(request.password());
        String profilePicture = null;
        try {
            if (!request.profilePicture().isEmpty()) {
                profilePicture = cloudinaryService.uploadImage(request.profilePicture());
            }
        } catch (IOException e) {
            throw new UploadFailedException(e.getMessage(), e);
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .dateOfBirth(request.dateOfBirth())
                .otherName(request.otherName())
                .about(request.about())
                .employeeEmail(request.personalEmail())
                .personalEmail(request.personalEmail())
                .phoneNumber(request.phoneNumber())
                .position(request.position())
                .maritalStatus(request.maritalStatus())
                .gender(request.gender())
                .homeAddress(request.homeAddress())
                .city(request.city())
                .zipCode(request.zipCode())
                .country(request.country())
                .regionOrState(request.regionOrState())
                .profilePicture(profilePicture)
                .employeeId(request.employeeId())
                .password(encodedPassword)
                .role(userRole)
                .isAdmin(request.role().equalsIgnoreCase("ADMIN"))
                .isVerified(false)
                .isBlocked(false)
                .build();

        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new UserOpsFailedException(e.getMessage(), e.getCause());
        }

        generateAndSendOTP(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SignUpResponse(OperationResultConstants.SUCCESS, savedUser.getId(), "OTP sent to your personalEmail. Please check your personalEmail"));
    }

    private void generateAndSendOTP(User user) {
        String employeeOtp = otpService.generateOTP(user.getEmployeeEmail());
        String personalOtp = otpService.generateOTP(user.getEmployeeEmail());
        sendOtp(user, employeeOtp);
        sendOtp(user, personalOtp);
    }

    private void sendOtp(User user, String otp) {
        CompletableFuture<Boolean> future = otpService.sendOTPByEmail(user.getEmployeeEmail(), otp, user.getFirstName() + " " + user.getLastName());
        try {
            boolean otpSent = future.get();
            if (!otpSent){
                throw new EmailFailedException("Email Failed to send. Try again");
            }
        } catch (ExecutionException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new OpsFailedException(e.getMessage(), e.getCause());
        }
    }

    public ResponseEntity<DefaultResponse> verify(OTPRequest request){
        boolean isEmployeeEmailConfirmed;
        boolean isPersonalEmailConfirmed;

        User user = userRepository.findUserById(request.userId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.isVerified()){
            isEmployeeEmailConfirmed = true;
            isPersonalEmailConfirmed = true;
        } else {
            try {
                isEmployeeEmailConfirmed = otpService.verifyOtp(request.employeeOtp(), user.getEmployeeEmail());
                isPersonalEmailConfirmed = otpService.verifyOtp(request.personalOtp(), user.getPersonalEmail());
            } catch (OTPExpiredException e) {
                generateAndSendOTP(user);
                return ResponseEntity.badRequest().body(new DefaultResponse(OperationResultConstants.FAIL.name(), "OTP has expired. \n Check your inbox for a new one"));
            }
        }
        if (isEmployeeEmailConfirmed && isPersonalEmailConfirmed){
            user.setVerified(true);
            userRepository.save(user);
            try {
                emailService.sendEmail("welcome.flth", user.getEmployeeEmail(), "Welcome to AmaliSecureSail", Map.of("user", user.getFirstName() + " " + user.getLastName()));
            } catch (TemplateException | MessagingException | IOException e) {
                throw new EmailFailedException(e.getMessage(), e.getCause());
            }
            return ResponseEntity.ok(new DefaultResponse(OperationResultConstants.SUCCESS.name(), "User verified successfully"));
        }
        throw new IllegalArgumentException("OTP is incorrect");
    }

    public ResponseEntity<DefaultResponse> getUser(String userId){
        User user = userRepository.findUserById(UUID.fromString(userId)).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName",user.getFirstName());
        userDetails.put("lastName", user.getLastName());
        userDetails.put("email", user.getEmployeeEmail());
        userDetails.put("phoneNumber", user.getPhoneNumber());
        userDetails.put("position", user.getPosition());
        userDetails.put("maritalStatus", user.getMaritalStatus());
        userDetails.put("gender", user.getGender());
        userDetails.put("homeAddress", user.getHomeAddress());
        userDetails.put("city", user.getCity());
        userDetails.put("zipCode", user.getZipCode());
        userDetails.put("country", user.getCountry());
        userDetails.put("regionOrState", user.getRegionOrState());
        userDetails.put("profilePicture", user.getProfilePicture());
        userDetails.put("dateOfBirth", user.getDateOfBirth());
        userDetails.put("employeeId", user.getEmployeeId());
        userDetails.put("Date Registered", user.getDateCreated());
        userDetails.put("About", user.getAbout());
        return ResponseEntity.ok(new DefaultResponse(OperationResultConstants.SUCCESS.name(), userDetails));
    }


}

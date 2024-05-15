package com.amalitech.securesail.amalisecuresail.authentication.services;

import com.amalitech.securesail.amalisecuresail.authentication.dtos.request.LoginRequest;
import com.amalitech.securesail.amalisecuresail.authentication.dtos.response.SignInResponse;
import com.amalitech.securesail.amalisecuresail.authentication.exceptions.JWTOpsFailedException;
import com.amalitech.securesail.amalisecuresail.global.dtos.response.DefaultResponse;
import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityNotFoundException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.UserNotVerifiedException;
import com.amalitech.securesail.amalisecuresail.user_management.repositories.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorizationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<SignInResponse>signIn(LoginRequest request) {

        User user = userRepository.findUserByEmployeeEmail(request.email()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.isBlocked()) {
            throw new EntityNotFoundException("YOur account is blocked. \n Please contact admin");
        }
        if (!user.isVerified()) {
            throw new UserNotVerifiedException("You are not verified");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authenticationToken);

        String token;
        try {
            token = jwtService.generateToken(user);
        } catch (JOSEException e) {
            throw new JWTOpsFailedException(e.getMessage(), e.getCause());
        }
        return ResponseEntity.ok(new SignInResponse("SUCCESS", token));
    }

    public ResponseEntity<DefaultResponse>signOut(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return ResponseEntity.ok(new DefaultResponse("SUCCESS", "You have been logged out"));
        }
        return ResponseEntity.ok(new DefaultResponse("FAILED", "You are not logged in"));
    }
}

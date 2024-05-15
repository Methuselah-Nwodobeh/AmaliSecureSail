package com.amalitech.securesail.amalisecuresail.runners;

import com.amalitech.securesail.amalisecuresail.user_management.constants.MaritalStatus;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.SignupRequest;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.response.SignUpResponse;
import com.amalitech.securesail.amalisecuresail.user_management.constants.Gender;
import com.amalitech.securesail.amalisecuresail.user_management.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSeeder {
    private final UserService userService;

    public void seed() {
        SignupRequest superAdmin = new SignupRequest(
                "Chukwu",
                "Emmanuel",
                "Ikeoma",
                "Software Engineer",
                "admin.admin@amalitech.com",
                "signimigne@gufum.com",
                "08123456789",
                "SC-ACC-1234",
                "ChukwuIbuso@1",
                LocalDate.of(2000, 1, 1),
                MaritalStatus.MARRIED,
                "SUPER_ADMIN",
                "I'm the undefeatable King of the world",
                Gender.FEMALE,
                null,
                "Banare",
                "Lagos",
                "23456",
                "Nigeria",
                "Lagos"

        );
        seedUser(superAdmin);
    }

    private void seedUser(SignupRequest signupRequest) {
        log.info("////////////////////////////////////////////////////////////////////////////////////");
        log.info("Seeding user");

        try {
            log.info("user about to create");
            ResponseEntity<SignUpResponse> response = userService.register(signupRequest);
            log.info("user created successfully");
            log.info(Objects.requireNonNull(response.getBody()).toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

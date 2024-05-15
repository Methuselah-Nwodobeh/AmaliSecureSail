package com.amalitech.securesail.amalisecuresail.user_management.dtos.requests;

import com.amalitech.securesail.amalisecuresail.user_management.constants.MaritalStatus;
import com.amalitech.securesail.amalisecuresail.user_management.constants.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record SignupRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        String otherName,
        @NotBlank String position,
        @NotBlank String employeeEmail,
        @NotBlank String personalEmail,
        @NotNull String phoneNumber,
        @NotBlank String employeeId,
        @NotBlank String password,
        java.time.LocalDate dateOfBirth,
        @NotNull MaritalStatus maritalStatus,
        @NotBlank String role,
        String about,
        Gender gender,
        MultipartFile profilePicture,
        @NotBlank String homeAddress,
        @NotBlank String city,
        @NotBlank String zipCode,
        @NotBlank String country,
        @NotBlank String regionOrState
) {
}
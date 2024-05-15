package com.amalitech.securesail.amalisecuresail.user_management.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OTPRequest(
        @NotBlank String employeeOtp,
        @NotNull UUID userId,
        @NotBlank String personalOtp
) {
}

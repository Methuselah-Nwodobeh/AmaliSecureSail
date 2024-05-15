package com.amalitech.securesail.amalisecuresail.authentication.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String email,
        @NotBlank String password) {
}

package com.amalitech.securesail.amalisecuresail.authentication.dtos.response;

import jakarta.validation.constraints.NotNull;

public record SignInResponse(
        String status,
        @NotNull String accessToken
) {
}

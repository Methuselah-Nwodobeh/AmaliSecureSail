package com.amalitech.securesail.amalisecuresail.global.dtos.response;

import jakarta.validation.constraints.NotBlank;

public record DefaultResponse (
        @NotBlank String status,
        @NotBlank Object body
) {
}
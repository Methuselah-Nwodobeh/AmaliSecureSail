package com.amalitech.securesail.amalisecuresail.user_management.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RoleRequest(
        @NotBlank String name,
        @NotBlank String description,
        @NotNull List<String> permissions
) {
}
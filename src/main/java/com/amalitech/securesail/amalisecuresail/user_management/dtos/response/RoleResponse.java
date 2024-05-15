package com.amalitech.securesail.amalisecuresail.user_management.dtos.response;

import com.amalitech.securesail.amalisecuresail.global.constants.OperationResultConstants;
import jakarta.validation.constraints.NotNull;

public record RoleResponse(
        @NotNull OperationResultConstants status,
        java.util.UUID roleId
) {
}
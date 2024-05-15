package com.amalitech.securesail.amalisecuresail.user_management.exceptions;

import java.io.Serial;

public class UserOpsFailedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UserOpsFailedException(String message) {
        super(message);
    }

    public UserOpsFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

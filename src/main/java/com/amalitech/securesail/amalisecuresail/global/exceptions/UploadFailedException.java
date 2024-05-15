package com.amalitech.securesail.amalisecuresail.global.exceptions;

import java.io.Serial;

public class UploadFailedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UploadFailedException(String message) {
        super(message);
    }

    public UploadFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

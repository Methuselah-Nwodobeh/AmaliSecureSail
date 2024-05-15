package com.amalitech.securesail.amalisecuresail.logging.exceptions;

import java.io.Serial;

public class LoggingFailedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public LoggingFailedException(String message) {
        super(message);
    }

    public LoggingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

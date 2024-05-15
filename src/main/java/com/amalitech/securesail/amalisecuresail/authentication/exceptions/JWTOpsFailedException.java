package com.amalitech.securesail.amalisecuresail.authentication.exceptions;

import java.io.Serial;

public class JWTOpsFailedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public JWTOpsFailedException(String message){
        super(message);
    }
    public JWTOpsFailedException(String message, Throwable cause){
        super(message, cause);
    }
}

package com.amalitech.securesail.amalisecuresail.authentication.exceptions;


import java.io.Serial;

public class UserNotVerifiedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public UserNotVerifiedException(String message){
        super(message);
    }
    public UserNotVerifiedException(String message, Throwable cause){
        super(message, cause);
    }
}
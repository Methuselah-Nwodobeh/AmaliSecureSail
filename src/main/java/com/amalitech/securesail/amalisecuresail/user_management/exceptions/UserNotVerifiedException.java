package com.amalitech.securesail.amalisecuresail.user_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
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
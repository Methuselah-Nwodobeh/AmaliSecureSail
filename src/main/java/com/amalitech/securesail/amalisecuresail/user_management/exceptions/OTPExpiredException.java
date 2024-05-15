package com.amalitech.securesail.amalisecuresail.user_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OTPExpiredException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public OTPExpiredException(String message){
        super(message);
    }
    public OTPExpiredException(String message, Throwable cause){
        super(message, cause);
    }
}
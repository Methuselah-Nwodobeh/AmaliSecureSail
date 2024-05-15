package com.amalitech.securesail.amalisecuresail.global.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailFailedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public EmailFailedException(String message){
        super(message);
    }

    public EmailFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
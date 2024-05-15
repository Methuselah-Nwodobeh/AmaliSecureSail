package com.amalitech.securesail.amalisecuresail.global.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OpsFailedException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public OpsFailedException(String message){
         super(message);
     }
     public OpsFailedException(String message, Throwable cause){
         super(message, cause);
     }
}
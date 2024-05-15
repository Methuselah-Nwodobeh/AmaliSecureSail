package com.amalitech.securesail.amalisecuresail.user_management.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyExistsException extends RuntimeException{
     @Serial
    private static final long serialVersionUID = 1L;

     public EntityAlreadyExistsException(String message){
         super(message);
     }
     public EntityAlreadyExistsException(String message, Throwable cause){
         super(message, cause);
     }
}
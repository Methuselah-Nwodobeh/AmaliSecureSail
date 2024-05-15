package com.amalitech.securesail.amalisecuresail.global.exceptions;

import com.amalitech.securesail.amalisecuresail.authentication.exceptions.JWTOpsFailedException;
import com.amalitech.securesail.amalisecuresail.authentication.exceptions.UserNotVerifiedException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityAlreadyExistsException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.OTPExpiredException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.UserOpsFailedException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.rmi.UnexpectedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, JWTOpsFailedException.class, EmailFailedException.class, UnexpectedException.class, OpsFailedException.class})
    public final ResponseEntity<Object> handlerAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return response("FAIL", HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class, UserOpsFailedException.class})
    public final ResponseEntity<Object> handleConflicts(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return response("CONFLICT", HttpStatus.CONFLICT, errorResponse);
    }

    @ExceptionHandler({OTPExpiredException.class, UserNotVerifiedException.class, OAuth2AuthenticationException.class, AuthenticationException.class, InsufficientAuthenticationException.class, BadCredentialsException.class})
    public final ResponseEntity<Object> handleAuthExceptions(Exception ex, WebRequest request) {
        ex.getCause();
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return response("ERROR", HttpStatus.UNAUTHORIZED, errorResponse);
    }

    private static ResponseEntity<Object> response(String status, HttpStatus httpStatus, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        return new ResponseEntity<>(response, httpStatus);
    }
}

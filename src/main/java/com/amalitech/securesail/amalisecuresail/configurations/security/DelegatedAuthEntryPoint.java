package com.amalitech.securesail.amalisecuresail.configurations.security;

import com.amalitech.securesail.amalisecuresail.authentication.exceptions.JWTOpsFailedException;
import com.amalitech.securesail.amalisecuresail.authentication.exceptions.UserNotVerifiedException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.EntityAlreadyExistsException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.OTPExpiredException;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.UserOpsFailedException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.text.ParseException;

@Component("delegatedAuthenticationEntryPoint")
@Slf4j()
public class DelegatedAuthEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    public DelegatedAuthEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.resolver = exceptionResolver;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {

        Object authError = request.getAttribute("error");
        switch (authError) {
            case ParseException parseException ->
                    resolver.resolveException(request, response, null, new ParseException(parseException.getMessage(), parseException.getErrorOffset()));
            case BadJOSEException badJOSEException ->
                    resolver.resolveException(request, response, null, new BadJOSEException(badJOSEException.getMessage()));
            case JOSEException joseException ->
                    resolver.resolveException(request, response, null, new JOSEException(joseException.getMessage()));
            case InsufficientAuthenticationException insufficientAuthenticationException ->
                    resolver.resolveException(request, response, null, new InsufficientAuthenticationException(insufficientAuthenticationException.getMessage(), insufficientAuthenticationException.getCause()));
            case JWTOpsFailedException jwtFailException ->
                    resolver.resolveException(request, response, null, new JWTOpsFailedException(jwtFailException.getMessage(), jwtFailException.getCause()));
            case AccessDeniedException accessDeniedException ->
                    resolver.resolveException(request, response, null, new AccessDeniedException(accessDeniedException.getMessage(), accessDeniedException.getCause()));
            case OTPExpiredException otpExpiredException ->
                    resolver.resolveException(request, response, null, new OTPExpiredException(otpExpiredException.getMessage(), otpExpiredException.getCause()));
            case EntityAlreadyExistsException entityAlreadyExistsException ->
                    resolver.resolveException(request, response, null, new EntityAlreadyExistsException(entityAlreadyExistsException.getMessage()));
            case UserNotVerifiedException userNotVerifiedException ->
                    resolver.resolveException(request, response, null, new UserNotVerifiedException(userNotVerifiedException.getMessage(), userNotVerifiedException.getCause()));
            case UserOpsFailedException userOpsFailedException ->
                    resolver.resolveException(request, response, null, new UserOpsFailedException(userOpsFailedException.getMessage(), userOpsFailedException.getCause()));
            case OAuth2AuthenticationException oAuth2AuthenticationException ->
                    resolver.resolveException(request, response, null, new OAuth2AuthenticationException(oAuth2AuthenticationException.getMessage()));
            case null, default -> resolver.resolveException(request, response, null, authException);
        }

    }
}
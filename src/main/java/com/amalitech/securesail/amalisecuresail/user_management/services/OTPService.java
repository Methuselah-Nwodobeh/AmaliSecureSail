package com.amalitech.securesail.amalisecuresail.user_management.services;

import com.amalitech.securesail.amalisecuresail.global.exceptions.OpsFailedException;
import com.amalitech.securesail.amalisecuresail.global.services.EmailService;
import com.amalitech.securesail.amalisecuresail.global.services.RedisService;
import com.amalitech.securesail.amalisecuresail.user_management.exceptions.OTPExpiredException;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class OTPService {
    private final EmailService emailService;
    private final RedisService redisService;
    private final Random random = new Random();

    public String generateOTP(String email) {

        int otpValue = 100_000 + random.nextInt(900_000);
        String otp = String.valueOf(otpValue);

        // Save the new OTP information in the database
        redisService.setValue(email, otp, Duration.ofMinutes(10));
        return otp;
    }

    @Async
    public CompletableFuture<Boolean> sendOTPByEmail(String email, String otp, String user) {
        // Compose the personalEmail content
        String subject = "Confirm Your Email Address for Mini";
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("OTP", otp);

        CompletableFuture<Void> emailSendingFuture;
        try {
            emailSendingFuture = emailService.sendEmail("otp.flth", email, subject, model);
        } catch (TemplateException | IOException | MessagingException e) {
            throw new OpsFailedException(e.getMessage(), e);
        }

        return emailSendingFuture.thenApplyAsync(result -> true)
                .exceptionally(ex -> false);
    }

    private String getOtp(String userId) {
        try {
            Optional<String> otpOps = Optional.of(redisService.getValue(userId));
            redisService.deleteValue(userId);
            return otpOps.orElseThrow(() -> new NullPointerException("The OTP might be null"));
        } catch (Exception e) {
            throw new OTPExpiredException("The OTP might be expired");
        }
    }

    public Boolean verifyOtp(String incoming, String userId) {
        return Objects.equals(incoming, getOtp(userId));
    }
}
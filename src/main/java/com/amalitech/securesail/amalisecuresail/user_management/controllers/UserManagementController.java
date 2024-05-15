package com.amalitech.securesail.amalisecuresail.user_management.controllers;

import com.amalitech.securesail.amalisecuresail.global.dtos.response.DefaultResponse;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.OTPRequest;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.requests.SignupRequest;
import com.amalitech.securesail.amalisecuresail.user_management.dtos.response.SignUpResponse;
import com.amalitech.securesail.amalisecuresail.user_management.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/")
public class UserManagementController {
    private final UserService userService;

    @PostMapping(value = "register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "register")
    public ResponseEntity<SignUpResponse> register(@Valid @RequestBody SignupRequest request) {
        return userService.register(request);
    }

    @PostMapping("verify")
    @Operation(summary = "verify user")
    public ResponseEntity<DefaultResponse> verify(@Valid @RequestBody OTPRequest request) {
        return userService.verify(request);
    }

    @GetMapping("get/{userId}")
    @Operation(summary = "verify user")
    public ResponseEntity<DefaultResponse> get(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }
}

package com.amalitech.securesail.amalisecuresail.authentication;

import com.amalitech.securesail.amalisecuresail.authentication.dtos.request.LoginRequest;
import com.amalitech.securesail.amalisecuresail.authentication.dtos.response.SignInResponse;
import com.amalitech.securesail.amalisecuresail.authentication.services.AuthorizationService;
import com.amalitech.securesail.amalisecuresail.global.dtos.response.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
    private final AuthorizationService authorizationService;

    @PostMapping("/login")
    @Operation(summary = "login")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody LoginRequest request) {
            return authorizationService.signIn(request);
    }

    @GetMapping("/logout")
    @Operation(summary = "logout")
    public ResponseEntity<DefaultResponse> logout(){
         return authorizationService.signOut();
     }
}

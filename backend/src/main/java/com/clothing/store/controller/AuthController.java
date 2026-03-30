package com.clothing.store.controller;

import com.clothing.store.dto.auth.AuthResponse;
import com.clothing.store.dto.auth.LoginRequest;
import com.clothing.store.dto.auth.RegisterRequest;
import com.clothing.store.dto.user.UserMeResponse;
import com.clothing.store.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserMeResponse me(Authentication authentication) {
        return authService.me(authentication.getName());
    }
}

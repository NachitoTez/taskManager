package com.lemon.taskmanager.auth.controller;

import com.lemon.taskmanager.auth.controller.dto.AuthRequest;
import com.lemon.taskmanager.auth.controller.dto.EnvironmentResponse;
import com.lemon.taskmanager.auth.controller.dto.RegisterRequest;
import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.auth.service.AuthService;
import com.lemon.taskmanager.user.domain.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        LOGGER.info("Login request received for user: {}", request.username());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        LOGGER.info("Register request received for user: {}", request.username());
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/environment")
    public EnvironmentResponse getEnvironment(@AuthenticationPrincipal User user) {
        LOGGER.info("GET /auth/environment requested by '{}'", user.getUsername());
        return authService.getEnvironment(user);
    }

}

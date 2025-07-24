package com.lemon.taskmanager.auth.service;

import com.lemon.taskmanager.auth.controller.dto.AuthRequest;
import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.auth.controller.dto.EnvironmentResponse;
import com.lemon.taskmanager.auth.controller.dto.RegisterRequest;
import com.lemon.taskmanager.user.domain.User;

public interface AuthService {
    AuthResponse login(AuthRequest request);
    AuthResponse register(RegisterRequest request);
    EnvironmentResponse getEnvironment(User user);
}

package com.lemon.taskmanager.auth.service;

import com.lemon.taskmanager.auth.dto.AuthRequest;
import com.lemon.taskmanager.auth.dto.AuthResponse;
import com.lemon.taskmanager.auth.dto.RegisterRequest;
import com.lemon.taskmanager.exceptions.UsernameAlreadyTakenException;
import com.lemon.taskmanager.user.model.User;
import com.lemon.taskmanager.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserService userService,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userService.findByUsername(request.username());

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByUsername(request.username())) {
            throw new UsernameAlreadyTakenException(request.username());
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User newUser = new User(request.username(), hashedPassword);
        User savedUser = userService.save(newUser);

        String token = jwtService.generateToken(savedUser);
        return new AuthResponse(token);
    }

}

package com.lemon.taskmanager.auth.service;

import com.lemon.taskmanager.auth.controller.dto.AuthRequest;
import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.auth.controller.dto.RegisterRequest;
import com.lemon.taskmanager.exceptions.UsernameAlreadyTakenException;
import com.lemon.taskmanager.user.repository.model.UserEntity;
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
        UserEntity userEntity = userService.findByUsername(request.username());

        if (!passwordEncoder.matches(request.password(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        //TODO acá podría mapear a mi nuevo user de dominio para nunca más tocar este userEntity

        String token = jwtService.generateToken(userEntity);

        return new AuthResponse(token);
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByUsername(request.username())) {
            throw new UsernameAlreadyTakenException(request.username());
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        UserEntity newUserEntity = new UserEntity(request.username(), hashedPassword);
        UserEntity savedUserEntity = userService.save(newUserEntity);

        String token = jwtService.generateToken(savedUserEntity);
        return new AuthResponse(token);
    }

}

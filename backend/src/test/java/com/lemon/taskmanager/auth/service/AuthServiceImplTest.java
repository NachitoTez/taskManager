package com.lemon.taskmanager.auth.service;

import com.lemon.taskmanager.auth.controller.dto.AuthRequest;
import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private AuthServiceImpl authService;

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);

        authService = new AuthServiceImpl(userService, passwordEncoder, jwtService);
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        String username = "anakin.skywalker";
        String rawPassword = "iAmTheChosenOne";
        String encodedPassword = "$2a$10$hashedPasswordFromTheDarkSide";
        String expectedToken = "jwt-token-for-darth-vader";

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        //TODO esto lo cambio seguro
        userEntity.setPassword(encodedPassword);

        when(userService.findUserEntityByUsername(username)).thenReturn(userEntity);
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(userEntity)).thenReturn(expectedToken);

        AuthRequest request = new AuthRequest(username, rawPassword);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(expectedToken, response.token());
        verify(userService).findUserEntityByUsername(username);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtService).generateToken(userEntity);
    }

}

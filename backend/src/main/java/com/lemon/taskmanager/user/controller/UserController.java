package com.lemon.taskmanager.user.controller;

import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.service.UserService;
import com.lemon.taskmanager.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/project-members")
    public List<UserResponse> getProjectMembers(@AuthenticationPrincipal User user) {
        LOGGER.info("GET /users/project-members requested by '{}'", user.getUsername());

        return userService.getProjectMembers(user.getId()).stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAllUsers();

        LOGGER.info("GET /users -> returning {} users", users.size());

        List<UserResponse> responses = users.stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }
}

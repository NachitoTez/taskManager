package com.lemon.taskmanager.user.controller;

import com.lemon.taskmanager.tasks.domain.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        Role role
) {}

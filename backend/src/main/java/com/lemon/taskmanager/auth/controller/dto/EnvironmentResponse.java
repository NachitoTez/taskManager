package com.lemon.taskmanager.auth.controller.dto;

import com.lemon.taskmanager.tasks.domain.Role;

import java.util.List;
import java.util.UUID;

public record EnvironmentResponse(
        UUID userId,
        String username,
        Role role,
        List<UUID> projectIds
) {}

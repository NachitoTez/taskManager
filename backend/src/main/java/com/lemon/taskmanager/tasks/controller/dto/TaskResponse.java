package com.lemon.taskmanager.tasks.controller.dto;

import com.lemon.taskmanager.tasks.domain.TaskStatus;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        String createdBy,
        String assignedTo,
        String componentName
) {}

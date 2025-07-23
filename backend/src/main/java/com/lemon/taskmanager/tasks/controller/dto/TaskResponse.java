package com.lemon.taskmanager.tasks.controller.dto;

import com.lemon.taskmanager.tasks.domain.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        String createdBy,
        String assignedTo,
        String componentName
) {}

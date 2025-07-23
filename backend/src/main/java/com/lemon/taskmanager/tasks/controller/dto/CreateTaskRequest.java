package com.lemon.taskmanager.tasks.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTaskRequest(
        @NotBlank String title,
        @NotBlank String description,
        @NotNull Long componentId,
        Long assigneeId
) {}

package com.lemon.taskmanager.tasks.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTaskRequest(
        @NotBlank String title,
        @NotBlank String description,
//        @NotNull UUID componentId,
        UUID assigneeId
) {}

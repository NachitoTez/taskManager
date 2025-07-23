package com.lemon.taskmanager.tasks.controller.dto;

import com.lemon.taskmanager.tasks.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull TaskStatus status) {}

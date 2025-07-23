package com.lemon.taskmanager.tasks.controller.dto;

import java.util.UUID;

public record CreateComponentRequest(String name, UUID projectId) {}


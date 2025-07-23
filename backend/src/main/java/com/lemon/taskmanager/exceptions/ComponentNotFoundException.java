package com.lemon.taskmanager.exceptions;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException(@NotNull UUID id) {
        super("Component with ID " + id + " not found.");
    }
}

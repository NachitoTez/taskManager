package com.lemon.taskmanager.exceptions;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID taskId) {
        super("Task with ID " + taskId + " was not found.");
    }
}

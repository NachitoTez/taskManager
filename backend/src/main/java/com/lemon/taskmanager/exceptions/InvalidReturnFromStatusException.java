package com.lemon.taskmanager.exceptions;

import com.lemon.taskmanager.tasks.domain.TaskStatus;

public class InvalidReturnFromStatusException extends RuntimeException {
    public InvalidReturnFromStatusException(TaskStatus current) {
        super("Task is not in a returnable state: " + current);
    }
}
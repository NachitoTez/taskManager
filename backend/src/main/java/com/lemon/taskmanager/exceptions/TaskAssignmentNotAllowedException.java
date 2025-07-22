package com.lemon.taskmanager.exceptions;

public class TaskAssignmentNotAllowedException extends RuntimeException {
    public TaskAssignmentNotAllowedException(String message) {
        super(message);
    }
}

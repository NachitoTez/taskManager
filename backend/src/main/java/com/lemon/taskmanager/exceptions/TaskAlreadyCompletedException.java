package com.lemon.taskmanager.exceptions;

public class TaskAlreadyCompletedException extends RuntimeException {
    public TaskAlreadyCompletedException() {
        super("Cannot modify a completed task.");
    }
}

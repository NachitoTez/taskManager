package com.lemon.taskmanager.exceptions;

public class InvalidTaskTransitionException extends RuntimeException {
    public InvalidTaskTransitionException(String from, String to) {
        super("Invalid transition from " + from + " to " + to);
    }
}

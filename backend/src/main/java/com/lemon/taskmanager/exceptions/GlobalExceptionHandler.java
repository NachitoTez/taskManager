package com.lemon.taskmanager.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//TODO si esto crece mucho debería separarlo en handlers más acotados
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        LOGGER.warn("User not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<String> handleUsernameAlreadyTaken(UsernameAlreadyTakenException ex) {
        LOGGER.warn("Attempted to register with existing username: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT) // 409
                .body(ex.getMessage());
    }

    @ExceptionHandler(TaskAssignmentNotAllowedException.class)
    public ResponseEntity<String> handleAssignmentDenied(TaskAssignmentNotAllowedException ex) {
        LOGGER.warn("Assignment denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTaskTransitionException.class)
    public ResponseEntity<String> handleInvalidTransition(InvalidTaskTransitionException ex) {
        LOGGER.warn("Invalid task transition: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TaskAlreadyCompletedException.class)
    public ResponseEntity<String> handleTaskAlreadyCompleted(TaskAlreadyCompletedException ex) {
        LOGGER.warn("Attempted to modify completed task: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidReturnFromStatusException.class)
    public ResponseEntity<String> handleInvalidReturnFromStatus(InvalidReturnFromStatusException ex) {
        LOGGER.warn("Invalid return from task status: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
        LOGGER.warn("Task not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}

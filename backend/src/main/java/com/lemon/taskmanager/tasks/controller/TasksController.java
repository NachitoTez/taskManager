package com.lemon.taskmanager.tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    @GetMapping
    public ResponseEntity<String> getTasks() {
        LOGGER.info("Accessed /tasks endpoint");
        return ResponseEntity.ok("Todo ok");
    }
}

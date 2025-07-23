package com.lemon.taskmanager.tasks.controller;

import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.controller.dto.AssignTaskRequest;
import com.lemon.taskmanager.tasks.controller.dto.TaskResponse;
import com.lemon.taskmanager.tasks.controller.dto.UpdateStatusRequest;
import com.lemon.taskmanager.tasks.service.TaskService;
import com.lemon.taskmanager.user.domain.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TasksController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getVisibleTasks(@AuthenticationPrincipal User user) {
        LOGGER.info("GET /tasks requested by '{}'", user.getUsername());

        List<TaskResponse> responses = taskService.getVisibleTasks(user).stream()
                .map(taskMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStatusRequest request,
            @AuthenticationPrincipal User user
    ) {
        LOGGER.info("PATCH /tasks/{}/status requested by '{}' to '{}'", id, user.getUsername(), request.status());

        taskService.updateTaskStatus(id, request.status(), user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<Void> assignTask(
            @PathVariable Long id,
            @RequestBody AssignTaskRequest request,
            @AuthenticationPrincipal User actor
    ) {
        LOGGER.info("PATCH /tasks/{}/assign requested by '{}' to assign to userId={}", id, actor.getUsername(), request.userId());

        //TODO Cambiar esto a futuro. Por ahora sirve
        User newAssignee = new User(request.userId(), null, null);
        taskService.assignTask(id, actor, newAssignee);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping
//    public ResponseEntity<TaskResponse> createTask(
//            @Valid @RequestBody CreateTaskRequest request,
//            @AuthenticationPrincipal User user
//    ) {
//        LOGGER.info("POST /tasks requested by '{}'", user.getUsername());
//        Task task = taskService.createTask(request, user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toResponse(task));
//    }

}

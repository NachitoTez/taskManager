package com.lemon.taskmanager.tasks.controller;

import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.controller.dto.*;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.service.TaskService;
import com.lemon.taskmanager.user.domain.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    //TODO esto lo comento porque como no está habilitada la parte de proyectos no tiene sentido
//    @GetMapping
//    public ResponseEntity<List<TaskResponse>> getVisibleTasks(@AuthenticationPrincipal User user) {
//        LOGGER.info("GET /tasks requested by '{}'", user.getUsername());
//
//        List<TaskResponse> responses = taskService.getVisibleTasks(user).stream()
//                .map(taskMapper::toResponse)
//                .toList();
//
//        return ResponseEntity.ok(responses);
//    }

    //TODO este lo agrego para salir del apuro. Podría implementarle una cache pero no vale la pena
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {

        List<TaskResponse> responses = taskService.getAllTasks().stream()
                .map(taskMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        LOGGER.info("GET /tasks/{} requested by '{}'", id, user.getUsername());

        Task task = taskService.getTaskById(id, user);
        return ResponseEntity.ok(taskMapper.toResponse(task));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateStatusRequest request,
            @AuthenticationPrincipal User user
    ) {
        LOGGER.info("PATCH /tasks/{}/status requested by '{}' to '{}'", id, user.getUsername(), request.status());

        taskService.updateTaskStatus(id, request.status(), user);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<Void> assignTask(
            @PathVariable UUID id,
            @RequestBody AssignTaskRequest request,
            @AuthenticationPrincipal User actor
    ) {
        LOGGER.info("PATCH /tasks/{}/assign requested by '{}' to assign to userId={}", id, actor.getUsername(), request.userId());

        //TODO Cambiar esto a futuro. Por ahora sirve
        User newAssignee = new User(request.userId(), null, null);
        taskService.assignTask(id, actor, newAssignee);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal User user
    ) {
        LOGGER.info("POST /tasks requested by '{}'", user.getUsername());
        Task task = taskService.createTask(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toResponse(task));
    }

//    @PostMapping("/components")
//    public ResponseEntity<ComponentResponse> createComponent(@RequestBody CreateComponentRequest request) {
//        ProjectEntity project = projectRepository.findById(request.projectId())
//                .orElseThrow(() -> new ProjectNotFoundException(request.projectId()));
//        UUID id = UUID.randomUUID();
//        ComponentEntity entity = new ComponentEntity(id, request.name(), project);
//        return ResponseEntity.status(HttpStatus.CREATED).body(componentMapper.toResponse(componentRepository.save(entity)));
//    }
//
//    @PostMapping("/projects")
//    public ResponseEntity<ProjectResponse> createProject(@RequestBody CreateProjectRequest request) {
//        UUID id = UUID.randomUUID();
//        ProjectEntity project = new ProjectEntity(id, request.name());
//        return ResponseEntity.status(HttpStatus.CREATED).body(projectMapper.toResponse(projectRepository.save(project)));
//    }

}

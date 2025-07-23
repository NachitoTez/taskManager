package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.ComponentNotFoundException;
import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.exceptions.TaskNotFoundException;
import com.lemon.taskmanager.mapper.ComponentMapper;
import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.controller.dto.CreateTaskRequest;
import com.lemon.taskmanager.tasks.domain.TaskComponent;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStateMachine;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.tasks.repository.ComponentRepository;
import com.lemon.taskmanager.tasks.repository.TaskRepository;
import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;
import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ComponentRepository componentRepository;
    private final UserService userService;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, ComponentRepository componentRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.componentRepository = componentRepository;
        this.userService = userService;

    }

    public List<Task> getVisibleTasks(User user) {
        LOGGER.info("Fetching visible tasks for user '{}'", user.getUsername());

        return taskRepository.findAll().stream()
                .map(taskMapper::toDomain)
                .filter(task -> task.canView(user))
                .collect(Collectors.toList());
    }

    public void updateTaskStatus(UUID taskId, TaskStatus newStatus, User actor) {
        LOGGER.info("User '{}' attempting to update task #{} to status '{}'", actor.getUsername(), taskId, newStatus);

        Task task = getDomainTaskOrThrow(taskId, "status update");

        if (!task.canEdit(actor)) {
            LOGGER.warn("User '{}' is not allowed to update task #{}", actor.getUsername(), taskId);
            throw new TaskAssignmentNotAllowedException("You do not have permission to modify this task.");
        }

        TaskStateMachine stateMachine = new TaskStateMachine(task, actor);
        stateMachine.transitionTo(newStatus);

        taskRepository.save(taskMapper.toEntity(task));
    }

    public void assignTask(UUID taskId, User actor, User newAssignee) {
        LOGGER.info("User '{}' attempting to assign task #{} to '{}'", actor.getUsername(), taskId, newAssignee.getUsername());

        Task task = getDomainTaskOrThrow(taskId, "assignment");

        task.assignTo(actor, newAssignee);

        taskRepository.save(taskMapper.toEntity(task));
    }

    public Task getTaskById(UUID taskId, User actor) {
        LOGGER.info("User '{}' fetching task #{}", actor.getUsername(), taskId);

        Task task = getDomainTaskOrThrow(taskId, "view");

        if (!task.canView(actor)) {
            LOGGER.warn("User '{}' is not allowed to view task #{}", actor.getUsername(), taskId);
            throw new TaskAssignmentNotAllowedException("You do not have permission to view this task.");
        }

        return task;
    }

    private Task getDomainTaskOrThrow(UUID taskId, String contextForLogging) {
        return taskRepository.findById(taskId)
                .map(taskMapper::toDomain)
                .orElseThrow(() -> {
                    LOGGER.warn("Task #{} not found during {}", taskId, contextForLogging);
                    return new TaskNotFoundException(taskId);
                });
    }

    public Task createTask(CreateTaskRequest request, User creator) {
        LOGGER.info("User '{}' creating task '{}'", creator.getUsername(), request.title());

        ComponentEntity componentEntity = componentRepository.findById(request.componentId())
                .orElseThrow(() -> new ComponentNotFoundException(request.componentId()));
        TaskComponent taskComponent = ComponentMapper.toDomain(componentEntity);

        User assignee = null;
        if (request.assigneeId() != null) {
            assignee = userService.findUserById(request.assigneeId());
        }

        // Crear tarea
        Task task = new Task(
                null,
                request.title(),
                request.description(),
                creator,
                assignee,
                taskComponent
        );
        task.setStatus(TaskStatus.BACKLOG);

        // Persistir
        TaskEntity entity = taskMapper.toEntity(task);
        TaskEntity saved = taskRepository.save(entity);

        return taskMapper.toDomain(saved);
    }


}

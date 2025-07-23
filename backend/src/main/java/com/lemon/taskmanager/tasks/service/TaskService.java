package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.exceptions.TaskNotFoundException;
import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.controller.dto.CreateTaskRequest;
import com.lemon.taskmanager.tasks.domain.Component;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStateMachine;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.tasks.repository.TaskRepository;
import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import com.lemon.taskmanager.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<Task> getVisibleTasks(User user) {
        LOGGER.info("Fetching visible tasks for user '{}'", user.getUsername());

        return taskRepository.findAll().stream()
                .map(taskMapper::toDomain)
                .filter(task -> task.canView(user))
                .collect(Collectors.toList());
    }

    public void updateTaskStatus(Long taskId, TaskStatus newStatus, User actor) {
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

    public void assignTask(Long taskId, User actor, User newAssignee) {
        LOGGER.info("User '{}' attempting to assign task #{} to '{}'", actor.getUsername(), taskId, newAssignee.getUsername());

        Task task = getDomainTaskOrThrow(taskId, "assignment");

        task.assignTo(actor, newAssignee);

        taskRepository.save(taskMapper.toEntity(task));
    }

    public Task getTaskById(Long taskId, User actor) {
        LOGGER.info("User '{}' fetching task #{}", actor.getUsername(), taskId);

        Task task = getDomainTaskOrThrow(taskId, "view");

        if (!task.canView(actor)) {
            LOGGER.warn("User '{}' is not allowed to view task #{}", actor.getUsername(), taskId);
            throw new TaskAssignmentNotAllowedException("You do not have permission to view this task.");
        }

        return task;
    }

    private Task getDomainTaskOrThrow(Long taskId, String contextForLogging) {
        return taskRepository.findById(taskId)
                .map(taskMapper::toDomain)
                .orElseThrow(() -> {
                    LOGGER.warn("Task #{} not found during {}", taskId, contextForLogging);
                    return new TaskNotFoundException(taskId);
                });
    }

    public Task createTask(CreateTaskRequest request, User creator) {
        LOGGER.info("User '{}' creating task '{}'", creator.getUsername(), request.title());

        User assignee = request.assigneeId() != null
                ? new User(request.assigneeId(), null, null)
                : null;

        Component component = new Component(request.componentId(), "placeholder", null);

        Task task = new Task(
                null,
                request.title(),
                request.description(),
                creator,
                assignee,
                component
        );

        task.setStatus(TaskStatus.BACKLOG);

        TaskEntity entity = taskMapper.toEntity(task);
        TaskEntity saved = taskRepository.save(entity);

        return taskMapper.toDomain(saved);
    }

}

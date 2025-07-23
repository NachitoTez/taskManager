package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStateMachine;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.tasks.model.TaskEntity;
import com.lemon.taskmanager.tasks.repository.TaskRepository;
import com.lemon.taskmanager.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }


    public List<Task> getVisibleTasks(User user) {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDomain)
                .filter(task -> task.canView(user))
                .collect(Collectors.toList());

    }

    public void updateTaskStatus(Long taskId, TaskStatus newStatus, User actor) {
        Task task = taskRepository.findById(taskId).map(taskMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        if (!task.canEdit(actor)) {
            throw new TaskAssignmentNotAllowedException("You do not have permission to modify this task.");
        }

        TaskStateMachine stateMachine = new TaskStateMachine(task, actor);
        stateMachine.transitionTo(newStatus);

        taskRepository.save(taskMapper.toEntity(task));
    }

    public void assignTask(Long taskId, User actor, User newAssignee) {
        TaskEntity entity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found: " + taskId));

        Task task = taskMapper.toDomain(entity);

        task.assignTo(actor, newAssignee);

        taskRepository.save(taskMapper.toEntity(task));
    }



}

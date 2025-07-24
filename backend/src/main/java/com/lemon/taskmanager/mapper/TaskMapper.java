package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import com.lemon.taskmanager.tasks.controller.dto.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final UserMapper userMapper;
    private final ComponentMapper componentMapper;
    private final ProjectMapper projectMapper;

    public TaskMapper(UserMapper userMapper, ComponentMapper componentMapper, ProjectMapper projectMapper) {
        this.userMapper = userMapper;
        this.componentMapper = componentMapper;
        this.projectMapper = projectMapper;
    }

    public Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                userMapper.toDomain(entity.getCreatedBy()),
                entity.getAssignedTo() != null ? userMapper.toDomain(entity.getAssignedTo()) : null,
                entity.getComponent() != null ? componentMapper.toDomain(entity.getComponent()) : null
        );
    }

    public TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                userMapper.toEntity(task.getCreatedBy()),
                task.getAssignedTo() != null ? userMapper.toEntity(task.getAssignedTo()) : null,
                task.getComponent() != null ? componentMapper.toEntity(task.getComponent(), projectMapper.toEntity(task.getComponent().getProject())) : null
        );
    }

    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedBy().getUsername(),
                task.getAssignedTo() != null ? task.getAssignedTo().getUsername() : null,
                task.getComponent() != null ? task.getComponent().getName() : null
        );
    }
}


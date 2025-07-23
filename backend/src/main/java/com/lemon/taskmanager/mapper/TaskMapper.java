package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import com.lemon.taskmanager.tasks.controller.dto.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                UserMapper.toDomain(entity.getCreatedBy()),
                UserMapper.toDomain(entity.getAssignedTo()),
                null //TODO componente por ahora null
        );
    }


    public TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                UserMapper.toEntity(task.getCreatedBy()),
                UserMapper.toEntity(task.getAssignedTo()),
                ComponentMapper.toEntity(task.getComponent())
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
                null
        );
    }

}

package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.model.TaskEntity;
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
                UserMapper.toEntity(task.getAssignedTo())
        );
    }
}

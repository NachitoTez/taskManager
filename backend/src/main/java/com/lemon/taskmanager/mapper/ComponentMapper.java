package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.TaskComponent;
import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;
import org.springframework.stereotype.Component;

@Component
public class ComponentMapper {

    public static TaskComponent toDomain(ComponentEntity entity) {
        return new TaskComponent(
                entity.getId(),
                entity.getName(),
                null //TODO terminar de pensar las relaciones
        );
    }

    public static ComponentEntity toEntity(TaskComponent taskComponent) {
        return new ComponentEntity(
                taskComponent.getId(),
                taskComponent.getName()
        );
    }
}

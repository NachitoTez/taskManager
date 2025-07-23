package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.Component;
import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;

public class ComponentMapper {

    public static Component toDomain(ComponentEntity entity) {
        return new Component(
                entity.getId(),
                entity.getName(),
                null //TODO terminar de pensar las relaciones
        );
    }

    public static ComponentEntity toEntity(Component component) {
        return new ComponentEntity(
                component.getId(),
                component.getName()
        );
    }
}

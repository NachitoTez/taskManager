package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.TaskComponent;
import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ComponentMapper {

    private final ProjectMapper projectMapper;

    public ComponentMapper(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    public TaskComponent toDomain(ComponentEntity entity) {
        return new TaskComponent(
                entity.getId(),
                entity.getName(),
                entity.getProject() != null ? projectMapper.toDomain(entity.getProject()) : null
        );
    }

    public ComponentEntity toEntity(TaskComponent taskComponent, ProjectEntity projectEntity) {
        return new ComponentEntity(
                taskComponent.getId(),
                taskComponent.getName(),
                projectEntity
        );
    }
}


package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.Project;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toDomain(ProjectEntity entity) {
        return new Project(
                entity.getId(),
                entity.getName()
        );
    }

    public ProjectEntity toEntity(Project domain) {
        return new ProjectEntity(
                domain.getId(),
                domain.getName()
        );
    }
}

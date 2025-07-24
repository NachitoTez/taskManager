package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.tasks.domain.Project;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private final UserMapper userMapper;


    public ProjectMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Project toDomain(ProjectEntity entity) {
        return new Project(
                entity.getId(),
                entity.getName(),
                userMapper.toDomain(entity.getCreator())
        );
    }

    public ProjectEntity toEntity(Project domain) {
        return new ProjectEntity(
                domain.getId(),
                userMapper.toEntity(domain.getCreator()),
                domain.getName()
        );
    }
}

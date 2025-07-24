package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.PermissionDeniedException;
import com.lemon.taskmanager.mapper.UserMapper;
import com.lemon.taskmanager.tasks.controller.dto.CreateProjectRequest;
import com.lemon.taskmanager.tasks.domain.Project;
import com.lemon.taskmanager.tasks.repository.ProjectRepository;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public ProjectService(ProjectRepository projectRepository, UserService userService, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public Project createProject(CreateProjectRequest request, User creator) {
        if (!creator.isManager()) {
            throw new PermissionDeniedException("Only managers can create projects");
        }

        UserEntity creatorEntity = userService.findUserEntityById(creator.getId());

        ProjectEntity entity = new ProjectEntity(UUID.randomUUID(),userMapper.toEntity(creator), request.name());
        entity.setMembers(Set.of(creatorEntity));

        ProjectEntity saved = projectRepository.save(entity);

        return new Project(saved.getId(), saved.getName(), creator);
    }
}

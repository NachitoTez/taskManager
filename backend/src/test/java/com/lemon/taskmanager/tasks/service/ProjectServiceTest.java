package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.PermissionDeniedException;
import com.lemon.taskmanager.factory.UserTestFactory;
import com.lemon.taskmanager.mapper.ProjectMapper;
import com.lemon.taskmanager.mapper.UserMapper;
import com.lemon.taskmanager.tasks.controller.dto.CreateProjectRequest;
import com.lemon.taskmanager.tasks.repository.ProjectRepository;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

class ProjectServiceTest {

    private ProjectService projectService;
    private ProjectRepository projectRepository;
    private UserService userService;
    private ProjectMapper projectMapper;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        userService = mock(UserService.class);
        projectMapper = mock(ProjectMapper.class);
        userMapper = mock(UserMapper.class);

        projectService = new ProjectService(projectRepository, userService,userMapper);
    }

    @Test
    void createProject_shouldThrowExceptionIfUserIsNotManager() {
        User member = UserTestFactory.memberWithName("anakin");
        CreateProjectRequest request = new CreateProjectRequest("Clone Army");

        assertThatThrownBy(() -> projectService.createProject(request, member))
                .isInstanceOf(PermissionDeniedException.class)
                .hasMessage("Only managers can create projects");

        verifyNoInteractions(userService);
        verifyNoInteractions(projectRepository);
        verifyNoInteractions(projectMapper);
    }

}

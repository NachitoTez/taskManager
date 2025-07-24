package com.lemon.taskmanager.auth.service;

import com.lemon.taskmanager.auth.controller.dto.AuthRequest;
import com.lemon.taskmanager.auth.controller.dto.AuthResponse;
import com.lemon.taskmanager.auth.controller.dto.EnvironmentResponse;
import com.lemon.taskmanager.tasks.domain.Role;
import com.lemon.taskmanager.tasks.repository.ProjectRepository;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    private AuthServiceImpl authService;

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private ProjectRepository projectRepository;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        projectRepository = mock(ProjectRepository.class);

        authService = new AuthServiceImpl(userService, passwordEncoder, jwtService, projectRepository);
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() {
        String username = "anakin.skywalker";
        String rawPassword = "iAmTheChosenOne";
        String encodedPassword = "$2a$10$hashedPasswordFromTheDarkSide";
        String expectedToken = "jwt-token-for-darth-vader";

        UserEntity userEntity = spy(new UserEntity());
        userEntity.setUsername(username);
        doReturn(true).when(userEntity).passwordMatches(rawPassword, passwordEncoder);

        when(userService.findUserEntityByUsername(username)).thenReturn(userEntity);
        when(jwtService.generateToken(userEntity)).thenReturn(expectedToken);

        AuthRequest request = new AuthRequest(username, rawPassword);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals(expectedToken, response.token());
        verify(userService).findUserEntityByUsername(username);
        verify(userEntity).passwordMatches(rawPassword, passwordEncoder);
        verify(jwtService).generateToken(userEntity);
    }

    @Test
    void getEnvironment_ShouldReturnUserDataWithProjectIds() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "vader", Role.MANAGER);
        UserEntity userEntity = new UserEntity("vader", "hashed", Role.MANAGER);  // o usar factory
        userEntity.setId(userId);

        UUID project1 = UUID.randomUUID();
        UUID project2 = UUID.randomUUID();

        ProjectEntity p1 = new ProjectEntity(project1, userEntity, "Death Star Project");
        ProjectEntity p2 = new ProjectEntity(UUID.randomUUID(), userEntity, "Executor Project");
        p2.setId(project2);

        List<ProjectEntity> mockProjects = List.of(p1, p2);

        when(projectRepository.findAllByMemberId(userId)).thenReturn(mockProjects);

        EnvironmentResponse response = authService.getEnvironment(user);

        assertThat(response.userId()).isEqualTo(userId);
        assertThat(response.username()).isEqualTo("vader");
        assertThat(response.role()).isEqualTo(Role.MANAGER);
        assertThat(response.projectIds()).containsExactlyInAnyOrder(project1, project2);

        verify(projectRepository).findAllByMemberId(userId);
    }


    @Test
    void login_ShouldThrowException_WhenPasswordIsInvalid() {
        String username = "luke.skywalker";
        String rawPassword = "wrongPassword";

        UserEntity userEntity = spy(new UserEntity());
        userEntity.setUsername(username);
        doReturn(false).when(userEntity).passwordMatches(rawPassword, passwordEncoder);

        when(userService.findUserEntityByUsername(username)).thenReturn(userEntity);

        AuthRequest request = new AuthRequest(username, rawPassword);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                authService.login(request)
        );

        assertEquals("Invalid credentials", exception.getMessage());

        verify(userService).findUserEntityByUsername(username);
        verify(userEntity).passwordMatches(rawPassword, passwordEncoder);
        verifyNoInteractions(jwtService);
    }

}

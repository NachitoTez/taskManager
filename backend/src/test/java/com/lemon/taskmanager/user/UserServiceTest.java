package com.lemon.taskmanager.user;

import com.lemon.taskmanager.factory.UserTestFactory;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.UserRepository;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.mapper.UserMapper;
import com.lemon.taskmanager.tasks.repository.ProjectRepository;
import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import com.lemon.taskmanager.user.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private UserMapper userMapper;
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        projectRepository = mock(ProjectRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper, projectRepository);
    }

    @Test
    void should_return_all_members_of_user_projects() {
        UUID userId = UUID.randomUUID();
        UUID project1Id = UUID.randomUUID();
        UUID project2Id = UUID.randomUUID();


        UserEntity vader = UserTestFactory.entityWithUsername("vader");
        UserEntity tarkin = UserTestFactory.entityWithUsername("tarkin");
        UserEntity leia = UserTestFactory.entityWithUsername("leia");

        ProjectEntity project1 = new ProjectEntity(project1Id, vader, "Death Star");
        ProjectEntity project2 = new ProjectEntity(project2Id,leia ,"Rebellion");

        project1.setMembers(Set.of(vader, tarkin));
        project2.setMembers(Set.of(leia, vader));

        when(projectRepository.findAllByMemberId(userId)).thenReturn(List.of(project1, project2));
        when(userMapper.toDomain(vader)).thenReturn(new User(vader.getId(), vader.getUsername(), vader.getRole()));
        when(userMapper.toDomain(tarkin)).thenReturn(new User(tarkin.getId(), tarkin.getUsername(), tarkin.getRole()));
        when(userMapper.toDomain(leia)).thenReturn(new User(leia.getId(), leia.getUsername(), leia.getRole()));

        List<User> result = userService.getProjectMembers(userId);

        assertEquals(3, result.size());
    }
}

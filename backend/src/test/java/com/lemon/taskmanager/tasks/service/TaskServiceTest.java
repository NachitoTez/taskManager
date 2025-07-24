package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.ComponentNotFoundException;
import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.exceptions.TaskNotFoundException;
import com.lemon.taskmanager.factory.UserTestFactory;
import com.lemon.taskmanager.mapper.ComponentMapper;
import com.lemon.taskmanager.mapper.ProjectMapper;
import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.controller.dto.CreateTaskRequest;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.factory.TaskTestFactory;
import com.lemon.taskmanager.tasks.repository.ComponentRepository;
import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;
import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import com.lemon.taskmanager.tasks.repository.TaskRepository;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private ComponentMapper componentMapper;
    private ProjectMapper projectMapper;
    private TaskService taskService;
    private ComponentRepository componentRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskMapper = mock(TaskMapper.class);
        componentMapper = mock(ComponentMapper.class);
        projectMapper = mock(ProjectMapper.class);
        componentRepository = mock(ComponentRepository.class);
        userService = mock(UserService.class);
        taskService = new TaskService(taskRepository, taskMapper, componentRepository, userService,componentMapper, projectMapper);
    }

    @Test
    void should_return_only_tasks_visible_to_user() {
        User mandalorian = UserTestFactory.memberWithName("Mandalorian");

        TaskEntity entity1 = mock(TaskEntity.class);
        TaskEntity entity2 = mock(TaskEntity.class);
        TaskEntity entity3 = mock(TaskEntity.class);

        Task taskVisible1 = mock(Task.class);
        Task taskVisible2 = mock(Task.class);
        Task taskHidden = mock(Task.class);

        when(taskRepository.findAll()).thenReturn(List.of(entity1, entity2, entity3));

        when(taskMapper.toDomain(entity1)).thenReturn(taskVisible1);
        when(taskMapper.toDomain(entity2)).thenReturn(taskVisible2);
        when(taskMapper.toDomain(entity3)).thenReturn(taskHidden);

        when(taskVisible1.canView(mandalorian)).thenReturn(true);
        when(taskVisible2.canView(mandalorian)).thenReturn(true);
        when(taskHidden.canView(mandalorian)).thenReturn(false);

        List<Task> result = taskService.getVisibleTasks(mandalorian);

        assertThat(result).containsExactlyInAnyOrder(taskVisible1, taskVisible2);
        assertThat(result).doesNotContain(taskHidden);
    }


    @Test
    void should_update_status_if_user_can_edit_real_task() {
        User anakin = UserTestFactory.memberWithName("Anakin");
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, anakin, anakin);

        UUID taskId = UUID.randomUUID();
        TaskEntity taskEntity = mock(TaskEntity.class);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toDomain(taskEntity)).thenReturn(task);
        when(taskMapper.toEntity(task)).thenReturn(taskEntity);

        taskService.updateTaskStatus(taskId, TaskStatus.IN_PROGRESS, anakin);

        assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        verify(taskRepository).save(taskEntity);
    }


    @Test
    void should_allow_member_to_assign_unassigned_task_to_self() {
        User boba = UserTestFactory.memberWithName("Han-Solo");
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, boba, null);
        UUID taskId = UUID.randomUUID();
        TaskEntity entity = mock(TaskEntity.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);
        when(taskMapper.toEntity(task)).thenReturn(entity);

        taskService.assignTask(taskId, boba, boba);

        assertThat(task.getAssignedTo()).isEqualTo(boba);
        verify(taskRepository).save(entity);
    }

    @Test
    void should_allow_manager_to_reassign_task() {
        User tarkin = UserTestFactory.managerWithName("Tarkin");
        User vader = UserTestFactory.memberWithName("Vader");
        User thrawn = UserTestFactory.memberWithName("Thrawn");
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, tarkin, thrawn);

        UUID taskId = UUID.randomUUID();
        TaskEntity entity = mock(TaskEntity.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);
        when(taskMapper.toEntity(task)).thenReturn(entity);

        taskService.assignTask(taskId, tarkin, vader);

        assertThat(task.getAssignedTo()).isEqualTo(vader);
        verify(taskRepository).save(entity);
    }

    @Test
    void should_not_allow_random_member_to_reassign_task() {
        User leia = UserTestFactory.memberWithName("Leia");
        User han = UserTestFactory.memberWithName("Han");
        User chewie = UserTestFactory.memberWithName("Chewbacca");

        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, leia, han);
        UUID taskId = UUID.randomUUID();
        TaskEntity entity = mock(TaskEntity.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);

        assertThrows(
                TaskAssignmentNotAllowedException.class,
                () -> taskService.assignTask(taskId, leia, chewie)
        );
        verify(taskRepository, never()).save(any());
    }

    @Test
    void should_throw_when_assigning_nonexistent_task() {
        User actor = UserTestFactory.memberWithName("Lando");
        User newAssignee = UserTestFactory.memberWithName("Rex");

        when(taskRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> taskService.assignTask(UUID.randomUUID(), actor, newAssignee)
        );
    }

    @Test
    void should_update_task_status_when_user_can_edit() {
        User anakin = UserTestFactory.memberWithName("Anakin");
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, anakin, anakin);
        UUID taskId = UUID.randomUUID();
        TaskEntity entity = mock(TaskEntity.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);
        when(taskMapper.toEntity(task)).thenReturn(entity);

        taskService.updateTaskStatus(taskId, TaskStatus.IN_PROGRESS, anakin);

        assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        verify(taskRepository).save(entity);
    }

    @Test
    void should_throw_if_user_cannot_edit_task() {
        User obiwan = UserTestFactory.memberWithName("Obi-wan");
        User mace = UserTestFactory.memberWithName("Mace Window");
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, mace, mace);

        UUID taskId = UUID.randomUUID();
        TaskEntity entity = mock(TaskEntity.class);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);

        assertThrows(TaskAssignmentNotAllowedException.class, () ->
                taskService.updateTaskStatus(taskId, TaskStatus.TESTING, obiwan)
        );

        verify(taskRepository, never()).save(any());
    }

    @Test
    void should_throw_if_task_not_found() {
        User yoda = UserTestFactory.managerWithName("Yoda");
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () ->
                taskService.updateTaskStatus(taskId, TaskStatus.TESTING, yoda)
        );
    }

    @Test
    void should_throw_if_user_cannot_view_task() {
        User finn = UserTestFactory.memberWithName("Finn");
        User poe = UserTestFactory.memberWithName("Poe");

        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, poe, poe); // finn no puede verla
        UUID taskId = UUID.randomUUID();
        TaskEntity entity = mock(TaskEntity.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);

        assertThrows(TaskAssignmentNotAllowedException.class, () -> taskService.getTaskById(taskId, finn));
    }

    @Test
    void should_create_task_with_optional_assignee() {
        User vader = UserTestFactory.memberWithName("Vader");
        User tarkin = UserTestFactory.managerWithName("Tarkin");

        CreateTaskRequest request = TaskTestFactory.createRequestWithAssignee(TaskTestFactory.componentEngineering(), tarkin);
        Task expectedTask = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, vader, tarkin);

        when(componentRepository.findById(request.componentId()))
                .thenReturn(Optional.of(mock(ComponentEntity.class)));

        TaskEntity entity = mock(TaskEntity.class);
        when(taskMapper.toEntity(any())).thenReturn(entity);
        when(taskRepository.save(entity)).thenReturn(entity);
        when(taskMapper.toDomain(entity)).thenReturn(expectedTask);

        Task result = taskService.createTask(request, vader);

        assertThat(result.getTitle()).isEqualTo(expectedTask.getTitle());
        assertThat(result.getStatus()).isEqualTo(TaskStatus.BACKLOG);
        assertThat(result.getCreatedBy()).isEqualTo(vader);
        assertThat(result.getAssignedTo()).isEqualTo(tarkin);

        verify(taskRepository).save(entity);
    }


    @Test
    void should_throw_exception_when_component_does_not_exist() {
        UUID fakeComponentId = UUID.randomUUID();
        CreateTaskRequest request = TaskTestFactory.createRequestWithoutAssignee(fakeComponentId);
        User creator = UserTestFactory.managerWithName("vader");

        when(componentRepository.findById(fakeComponentId)).thenReturn(Optional.empty());

        assertThrows(ComponentNotFoundException.class, () -> taskService.createTask(request, creator));
    }

}

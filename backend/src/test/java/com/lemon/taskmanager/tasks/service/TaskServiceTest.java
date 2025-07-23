package com.lemon.taskmanager.tasks.service;

import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.mapper.TaskMapper;
import com.lemon.taskmanager.tasks.domain.Role;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.tasks.domain.TaskTestFactory;
import com.lemon.taskmanager.tasks.model.TaskEntity;
import com.lemon.taskmanager.tasks.repository.TaskRepository;
import com.lemon.taskmanager.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskMapper taskMapper;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskService(taskRepository, taskMapper);
    }

    @Test
    void should_return_only_tasks_visible_to_user() {
        User mandalorian = new User(1L, "Mandalorian", Role.MEMBER);

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
        User anakin = new User(2L, "anakin", Role.MEMBER);
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, anakin, anakin);

        TaskEntity taskEntity = mock(TaskEntity.class);
        when(taskRepository.findById(42L)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toDomain(taskEntity)).thenReturn(task);
        when(taskMapper.toEntity(task)).thenReturn(taskEntity);

        taskService.updateTaskStatus(42L, TaskStatus.IN_PROGRESS, anakin);

        assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        verify(taskRepository).save(taskEntity);
    }

    @Test
    void should_allow_manager_to_reassign_task() {
        User tarkin = new User(1L, "tarkin", Role.MANAGER);
        User vader = new User(2L, "vader", Role.MEMBER);
        User thrawn = new User(3L, "thrawn", Role.MEMBER);

        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, tarkin, thrawn);

        TaskEntity entity = mock(TaskEntity.class);
        when(taskRepository.findById(42L)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);
        when(taskMapper.toEntity(task)).thenReturn(entity);

        taskService.assignTask(42L, tarkin, vader);

        assertThat(task.getAssignedTo()).isEqualTo(vader);
        verify(taskRepository).save(entity);
    }

    @Test
    void should_not_allow_random_member_to_reassign_task() {
        User leia = new User(1L, "leia", Role.MEMBER);
        User han = new User(2L, "han", Role.MEMBER);
        User chewie = new User(3L, "chewbacca", Role.MEMBER);

        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, leia, han);

        TaskEntity entity = mock(TaskEntity.class);

        when(taskRepository.findById(42L)).thenReturn(Optional.of(entity));
        when(taskMapper.toDomain(entity)).thenReturn(task);

        assertThrows(
                TaskAssignmentNotAllowedException.class,
                () -> taskService.assignTask(42L, leia, chewie)
        );
        verify(taskRepository, never()).save(any());
    }


    @Test
    void should_throw_when_assigning_nonexistent_task() {
        User actor = new User(1L, "lando", Role.MANAGER);
        User newAssignee = new User(2L, "ackbar", Role.MEMBER);

        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> taskService.assignTask(99L, actor, newAssignee)
        );
    }

}

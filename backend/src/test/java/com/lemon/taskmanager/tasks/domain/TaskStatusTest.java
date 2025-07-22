package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.exceptions.InvalidTaskTransitionException;
import com.lemon.taskmanager.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    private final User anakin = new User(1L, "anakin", Role.MEMBER);
    private final User tarkin = new User(2L, "tarkin", Role.MANAGER);
    private final User vader = new User(3L, "vader", Role.MEMBER);

    @Test
    void should_allow_valid_transition_from_backlog_to_in_progress() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, tarkin, vader);
        TaskStateMachine sm = new TaskStateMachine(task, anakin);

        sm.transitionTo(TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, task.getStatus());
    }

    @Test
    void should_not_allow_transition_from_in_progress_to_done() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.IN_PROGRESS, tarkin, vader);
        TaskStateMachine sm = new TaskStateMachine(task, anakin);

        assertThrows(InvalidTaskTransitionException.class, () -> sm.transitionTo(TaskStatus.DONE));
    }

    @Test
    void should_allow_block_and_return_to_previous_status() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.TESTING, tarkin, vader);
        TaskStateMachine sm = new TaskStateMachine(task, anakin);

        sm.moveToBlocked();
        assertEquals(TaskStatus.BLOCKED, task.getStatus());

        sm.returnFromBlocked();
        assertEquals(TaskStatus.TESTING, task.getStatus());
    }

    @Test
    void should_create_task_with_default_status_backlog() {
        Task task = TaskTestFactory.simpleTask(tarkin, vader);
        assertEquals(TaskStatus.BACKLOG, task.getStatus());
    }

    @Test
    void should_move_to_waiting_info_and_return() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.TESTING, tarkin, vader);
        TaskStateMachine sm = new TaskStateMachine(task, anakin);

        sm.moveToWaitingInfo();
        assertEquals(TaskStatus.WAITING_INFO, task.getStatus());

        sm.returnFromWaitingInfo();
        assertEquals(TaskStatus.TESTING, task.getStatus());
    }

}

package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.exceptions.InvalidTaskTransitionException;
import com.lemon.taskmanager.factory.TaskTestFactory;
import com.lemon.taskmanager.factory.UserTestFactory;
import com.lemon.taskmanager.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusTest {

    private final User anakin = UserTestFactory.memberWithName("Anakin");
    private final User tarkin = UserTestFactory.managerWithName("Tarkin");
    private final User vader = UserTestFactory.memberWithName("Vader");

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

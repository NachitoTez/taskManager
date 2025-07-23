package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.factory.TaskTestFactory;
import com.lemon.taskmanager.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskAssignmentTest {

    private final User vader = new User(1L, "vader", Role.MEMBER);
    private final User tarkin = new User(2L, "tarkin", Role.MANAGER);
    private final User thrawn = new User(3L, "thrawn", Role.MEMBER);

    @Test
    void should_allow_anyone_to_assign_unassigned_task() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, vader, null);

        task.assignTo(thrawn, thrawn);

        assertEquals(thrawn, task.getAssignedTo());
    }

    @Test
    void should_allow_manager_to_reassign_task() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, vader, thrawn);

        task.assignTo(tarkin, vader);

        assertEquals(vader, task.getAssignedTo());
    }

    @Test
    void should_allow_current_assignee_to_reassign() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, vader, thrawn);

        task.assignTo(thrawn, vader);

        assertEquals(vader, task.getAssignedTo());
    }

    @Test
    void should_not_allow_vader_to_reassign_thrawns_task() {
        Task task = TaskTestFactory.simpleTask(TaskStatus.BACKLOG, tarkin, thrawn);

        assertThrows(TaskAssignmentNotAllowedException.class, () -> task.assignTo(vader, new User(4L, "yoda", Role.MEMBER)));
    }

}

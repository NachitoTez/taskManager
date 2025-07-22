package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.user.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskStateMachine {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskStateMachine.class);

    private final Task task;
    private final User actor;

    public TaskStateMachine(Task task, User actor) {
        this.task = task;
        this.actor = actor;
    }

    public void transitionTo(TaskStatus nextStatus) {
        if (!task.getStatus().canTransitionTo(nextStatus)) {
            LOGGER.warn("{} tried invalid transition from {} to {} on task {}",
                    actor.getUsername(), task.getStatus(), nextStatus, task.getId());
            throw new IllegalStateException("Invalid transition from " + task.getStatus() + " to " + nextStatus);
        }

        LOGGER.info("{} transitioned task {} from {} to {}", actor.getUsername(),
                task.getId(), task.getStatus(), nextStatus);

        task.setPreviousStatus(task.getStatus());
        task.setStatus(nextStatus);
    }

    public void moveToBlocked() {
        if (task.getStatus() == TaskStatus.DONE) {
            LOGGER.warn("Attempted to block completed task {}", task.getId());
            throw new IllegalStateException("Cannot block a completed task");
        }

        LOGGER.info("{} blocked task {} from status {}", actor.getUsername(), task.getId(), task.getStatus());

        task.setPreviousStatus(task.getStatus());
        task.setStatus(TaskStatus.BLOCKED);
    }

    public void returnFromBlocked() {
        if (task.getStatus() != TaskStatus.BLOCKED) {
            LOGGER.warn("Attempted to unblock task {} but it is in status {}", task.getId(), task.getStatus());
            throw new IllegalStateException("Task is not in BLOCKED state");
        }

        LOGGER.info("{} unblocked task {}, returning to {}", actor.getUsername(), task.getId(), task.getPreviousStatus());

        task.setStatus(task.getPreviousStatus());
        task.setPreviousStatus(null);
    }

    public void moveToWaitingInfo() {
        if (task.getStatus() == TaskStatus.DONE) {
            LOGGER.warn("Attempted to move completed task {} to WAITING_INFO", task.getId());
            throw new IllegalStateException("Cannot move a completed task to WAITING_INFO");
        }

        LOGGER.info("{} set task {} to WAITING_INFO from {}", actor.getUsername(), task.getId(), task.getStatus());

        task.setPreviousStatus(task.getStatus());
        task.setStatus(TaskStatus.WAITING_INFO);
    }

    public void returnFromWaitingInfo() {
        if (task.getStatus() != TaskStatus.WAITING_INFO) {
            LOGGER.warn("Attempted to return task {} from WAITING_INFO but was in {}", task.getId(), task.getStatus());
            throw new IllegalStateException("Task is not in WAITING_INFO state");
        }

        LOGGER.info("{} restored task {} to {}", actor.getUsername(), task.getId(), task.getPreviousStatus());

        task.setStatus(task.getPreviousStatus());
        task.setPreviousStatus(null);
    }
}

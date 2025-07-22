package com.lemon.taskmanager.tasks.domain;

import java.util.Set;

public enum TaskStatus {
    BLOCKED(Set.of()),
    WAITING_INFO(Set.of()),
    DONE(Set.of()),
    TESTING(Set.of(DONE, BLOCKED, WAITING_INFO)),
    IN_PROGRESS(Set.of(TESTING, BLOCKED, WAITING_INFO)),
    BACKLOG(Set.of(IN_PROGRESS, BLOCKED, WAITING_INFO));

    private final Set<TaskStatus> next;

    TaskStatus(Set<TaskStatus> next) {
        this.next = next;
    }

    public boolean canTransitionTo(TaskStatus nextStatus) {
        if (this == BLOCKED || this == WAITING_INFO) return false;
        return next.contains(nextStatus);
    }
}


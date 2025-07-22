package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.exceptions.TaskAssignmentNotAllowedException;
import com.lemon.taskmanager.user.domain.User;

public class Task {

    private final Long id;
    private final String title;
    private final String description;
    private final User createdBy;
    private User assignedTo;
    private final Component component;
    private TaskStatus status = TaskStatus.BACKLOG;
    private TaskStatus previousStatus;

    public Task(Long id, String title, String description, User createdBy, User assignedTo, Component component) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.component = component;
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public User getCreatedBy() { return createdBy; }

    public User getAssignedTo() { return assignedTo; }

    public Component getComponent() { return component; }

    public TaskStatus getStatus() { return status; }

    public void setStatus(TaskStatus status) { this.status = status; }

    public TaskStatus getPreviousStatus() { return previousStatus; }

    public void setPreviousStatus(TaskStatus previousStatus) { this.previousStatus = previousStatus; }


    public void assignTo(User actor, User newAssignee) {
        if (this.assignedTo == null) {
            this.assignedTo = newAssignee;
            return;
        }

        if (actor.getId().equals(assignedTo.getId()) || actor.isManager()) {
            this.assignedTo = newAssignee;
            return;
        }

        throw new TaskAssignmentNotAllowedException("Only the assignee or a manager can reassign this task.");
    }

    //TODO creo que tengo que corregirlo. Me gustaría armar la clase Team y validar eso acá
    public boolean canView(User user) {
        return user.getId().equals(createdBy.getId()) ||
                (assignedTo != null && user.getId().equals(assignedTo.getId())) ||
                component.getProject().isMember(user);
    }


    public boolean canEdit(User user) {
        return user.getId().equals(assignedTo != null ? assignedTo.getId() : null) ||
                user.isManager();
    }

}

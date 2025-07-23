package com.lemon.taskmanager.tasks.repository.model;

import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    private TaskStatus previousStatus;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to_id")
    private UserEntity assignedTo;

    @ManyToOne
    @JoinColumn(name = "component_id")
    private ComponentEntity component;

    public ComponentEntity getComponent() {
        return component;
    }

    public TaskEntity() {}

    public TaskEntity(String title, String description, TaskStatus status, UserEntity createdBy, UserEntity assignedTo, ComponentEntity component) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
        this.component = component;
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskStatus getPreviousStatus() {
        return previousStatus;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public UserEntity getAssignedTo() {
        return assignedTo;
    }
}

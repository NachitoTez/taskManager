package com.lemon.taskmanager.tasks.model;

import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.user.model.UserEntity;
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

    // TODO: agregar ComponentEntity cuando modele Component

    public TaskEntity() {}

    public TaskEntity(String title, String description, TaskStatus status, UserEntity createdBy, UserEntity assignedTo) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdBy = createdBy;
        this.assignedTo = assignedTo;
    }

}

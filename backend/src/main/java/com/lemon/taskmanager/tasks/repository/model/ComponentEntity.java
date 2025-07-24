package com.lemon.taskmanager.tasks.repository.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "components")
public class ComponentEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    public ComponentEntity() {}

    public ComponentEntity(UUID id, String name, ProjectEntity project) {
        this.id = id;
        this.name = name;
        this.project = project;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ProjectEntity getProject() {
        return project;
    }
}

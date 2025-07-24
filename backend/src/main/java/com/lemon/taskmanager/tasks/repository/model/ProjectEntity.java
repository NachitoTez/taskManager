package com.lemon.taskmanager.tasks.repository.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(name = "creator_id")
    private UUID creatorId; // simplificamos sin relación aún

    public ProjectEntity() {}

    public ProjectEntity(UUID creatorId, String name) {
        this.creatorId = creatorId;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getCreatorId() {
        return creatorId;
    }
}

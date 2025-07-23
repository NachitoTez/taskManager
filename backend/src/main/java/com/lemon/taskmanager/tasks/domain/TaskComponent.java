package com.lemon.taskmanager.tasks.domain;

import java.util.UUID;

public class TaskComponent {
    private final UUID id;
    private final String name;
    private final Project project;

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public TaskComponent(UUID id, String name, Project project) {
        this.id = id;
        this.name = name;
        this.project = project;
    }
}

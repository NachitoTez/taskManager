package com.lemon.taskmanager.tasks.domain;

public class Component {
    private final Long id;
    private final String name;
    private final Project project;

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Component(Long id, String name, Project project) {
        this.id = id;
        this.name = name;
        this.project = project;
    }
}

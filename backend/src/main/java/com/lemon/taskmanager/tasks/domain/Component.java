package com.lemon.taskmanager.tasks.domain;

public class Component {
    private final Long id;
    private final String title;
    private final Project project;

    public Project getProject() {
        return project;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public Component(Long id, String title, Project project) {
        this.id = id;
        this.title = title;
        this.project = project;
    }
}

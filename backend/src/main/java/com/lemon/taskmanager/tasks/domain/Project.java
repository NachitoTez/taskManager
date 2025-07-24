package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.user.domain.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Project {

    private final UUID id;
    private final String name;
    private final User creator;
    private final Set<User> members = new HashSet<>();

    public Project(UUID id, String name, User creator) {
        this.id = id;
        this.name = name;
        this.creator = creator;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getCreator() {
        return creator;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public boolean isMember(User user) {
        if (user == null || user.getId() == null) return false;
        return members.stream().anyMatch(u -> u.getId().equals(user.getId()));
    }

}

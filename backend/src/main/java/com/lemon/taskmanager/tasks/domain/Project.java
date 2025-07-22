package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.user.domain.User;

import java.util.HashSet;
import java.util.Set;

public class Project {

    private final Long id;
    private final String name;
    private final Set<User> members = new HashSet<>();

    public Project(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addMember(User user) {
        members.add(user);
    }

    public boolean isMember(User user) {
        return members.stream().anyMatch(u -> u.getId().equals(user.getId()));
    }
}


package com.lemon.taskmanager.user.domain;

import com.lemon.taskmanager.tasks.domain.Role;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String username;
    private final Role role;

    public User(UUID id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public boolean isManager() {
        return role == Role.MANAGER;
    }

    public boolean hasRole(Role expected) {
        return role == expected;
    }
}

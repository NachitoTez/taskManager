package com.lemon.taskmanager.user.domain;

import com.lemon.taskmanager.tasks.domain.Role;

public class User {

    private final Long id;
    private final String username;
    private final Role role;

    public User(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    public boolean isManager() {
        return role == Role.MANAGER || role == Role.ADMIN;
    }

    public boolean hasRole(Role expected) {
        return role == expected;
    }
}

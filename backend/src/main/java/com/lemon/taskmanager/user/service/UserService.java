package com.lemon.taskmanager.user.service;

import com.lemon.taskmanager.user.model.User;

public interface UserService {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    User save(User user);
}

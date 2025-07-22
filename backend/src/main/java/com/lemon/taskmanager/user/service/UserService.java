package com.lemon.taskmanager.user.service;

import com.lemon.taskmanager.user.model.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    UserEntity save(UserEntity userEntity);
}

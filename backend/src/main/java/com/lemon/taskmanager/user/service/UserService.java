package com.lemon.taskmanager.user.service;

import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.model.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity findUserEntityByUsername(String username);

    UserEntity findUserEntityById(UUID id);

    User findUserById(UUID id);

    User findUserByUsername(String username);

    boolean existsByUsername(String username);

    UserEntity save(UserEntity userEntity);
}

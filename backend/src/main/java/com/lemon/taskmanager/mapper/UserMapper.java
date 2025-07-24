package com.lemon.taskmanager.mapper;

import com.lemon.taskmanager.user.controller.UserResponse;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.model.UserEntity;
import com.lemon.taskmanager.tasks.domain.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getRole()
        );
    }

    public UserEntity toEntity(User domainUser) {
        UserEntity entity = new UserEntity();
        entity.setUsername(domainUser.getUsername());
        entity.setRole(domainUser.getRole());
        return entity;
    }

    public UserResponse toResponse(User domain) {
        return new UserResponse(
                domain.getId(),
                domain.getUsername(),
                domain.getRole()
        );
    }
}

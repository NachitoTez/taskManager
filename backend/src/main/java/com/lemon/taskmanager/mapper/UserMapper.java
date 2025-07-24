package com.lemon.taskmanager.mapper;

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
                Role.valueOf(entity.getRole())
        );
    }

    public UserEntity toEntity(User domainUser) {
        UserEntity entity = new UserEntity();
        entity.setUsername(domainUser.getUsername());
        entity.setRole(domainUser.getRole().name());
        return entity;
    }
}

package com.lemon.taskmanager.factory;

import com.lemon.taskmanager.tasks.domain.Role;
import com.lemon.taskmanager.user.domain.User;
import com.lemon.taskmanager.user.repository.model.UserEntity;

import java.util.UUID;

public class UserTestFactory {

    private static final String DUMMY_HASHED_PASSWORD = "hashed-password";

    public static User managerWithName(String name) {
        return new User(randomId(), name, Role.MANAGER);
    }

    public static User memberWithName(String name) {
        return new User(randomId(), name, Role.MEMBER);
    }


    public static UserEntity entityWithUsername(String username) {
        return new UserEntity(username, DUMMY_HASHED_PASSWORD, Role.MEMBER);
    }

    public static UserEntity managerEntityWithUsername(String username) {
        return new UserEntity(username, DUMMY_HASHED_PASSWORD, Role.MANAGER);
    }

    public static UserEntity memberEntityWithUsername(String username) {
        return new UserEntity(username, DUMMY_HASHED_PASSWORD, Role.MEMBER);
    }



    private static UUID randomId() {
        return UUID.randomUUID();
    }
}

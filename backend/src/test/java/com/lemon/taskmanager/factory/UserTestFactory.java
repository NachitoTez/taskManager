package com.lemon.taskmanager.factory;

import com.lemon.taskmanager.tasks.domain.Role;
import com.lemon.taskmanager.user.domain.User;

import java.util.UUID;

public class UserTestFactory {

    public static User managerWithName(String name) {
        return new User(randomId(), name, Role.MANAGER);
    }

    public static User memberWithName(String name) {
        return new User(randomId(), name, Role.MEMBER);
    }


    private static UUID randomId() {
        return UUID.randomUUID();
    }
}

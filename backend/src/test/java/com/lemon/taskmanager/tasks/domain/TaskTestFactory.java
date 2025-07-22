package com.lemon.taskmanager.tasks.domain;

import com.lemon.taskmanager.user.domain.User;

public class TaskTestFactory {

    public static Task simpleTask(TaskStatus status, User creator, User assignee) {
        Task task = new Task(
                42L,
                "Fix tractor beam",
                "Realign crystal array in Death Star",
                creator,
                assignee,
                new Component(900L, "Engineering", new Project(100L, "Death Star"))
        );
        task.setStatus(status);
        return task;
    }

    public static Task simpleTask(User creator, User assignee) {
        return new Task(
                43L,
                "Polish stormtrooper armor",
                "Make sure they shine before the next inspection",
                creator,
                assignee,
                new Component(901L, "Imperial Logistics", new Project(100L, "Death Star"))
        );
    }
    //TODO crear componente y proyecto
}

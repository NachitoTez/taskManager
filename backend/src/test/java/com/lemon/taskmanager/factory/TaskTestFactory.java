package com.lemon.taskmanager.factory;

import com.lemon.taskmanager.tasks.controller.dto.AssignTaskRequest;
import com.lemon.taskmanager.tasks.controller.dto.CreateTaskRequest;
import com.lemon.taskmanager.tasks.domain.Component;
import com.lemon.taskmanager.tasks.domain.Project;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
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

    public static Task taskInTesting(User creator, User assignee) {
        return simpleTask(TaskStatus.TESTING, creator, assignee);
    }

    public static Task unassignedBacklogTask(User creator) {
        return simpleTask(TaskStatus.BACKLOG, creator, null);
    }

    public static Component componentEngineering() {
        return new Component(900L, "Engineering", projectDeathStar());
    }

    public static Component componentLogistics() {
        return new Component(901L, "Imperial Logistics", projectDeathStar());
    }

    public static Project projectDeathStar() {
        return new Project(100L, "Death Star");
    }

    public static Project projectWithMembers(User... users) {
        return new Project(101L, "Starkiller Base");
    }

    // -------- DTOs --------

    public static CreateTaskRequest createRequestWithAssignee(Component component, User assignee) {
        return new CreateTaskRequest(
                "Install hyperdrive",
                "Connect power lines and run diagnostics",
                component.getId(),
                assignee.getId()
        );
    }

    public static CreateTaskRequest createRequestWithoutAssignee(Component component) {
        return new CreateTaskRequest(
                "Calibrate sensors",
                "Use protocol outlined in imperial manual section 3-B",
                component.getId(),
                null
        );
    }

    public static AssignTaskRequest assignRequestTo(User user) {
        return new AssignTaskRequest(user.getId());
    }

    public static AssignTaskRequest assignRequestTo(Long userId) {
        return new AssignTaskRequest(userId);
    }
}

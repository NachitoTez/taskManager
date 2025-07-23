package com.lemon.taskmanager.factory;

import com.lemon.taskmanager.tasks.controller.dto.AssignTaskRequest;
import com.lemon.taskmanager.tasks.controller.dto.CreateTaskRequest;
import com.lemon.taskmanager.tasks.domain.TaskComponent;
import com.lemon.taskmanager.tasks.domain.Project;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.user.domain.User;

import java.util.UUID;

public class TaskTestFactory {

    public static Task simpleTask(TaskStatus status, User creator, User assignee) {
        Task task = new Task(
                randomId(),
                "Fix tractor beam",
                "Realign crystal array in Death Star",
                creator,
                assignee,
                componentEngineering()
        );
        task.setStatus(status);
        return task;
    }

    public static Task simpleTask(User creator, User assignee) {
        return new Task(
                randomId(),
                "Polish stormtrooper armor",
                "Make sure they shine before the next inspection",
                creator,
                assignee,
                componentLogistics()
        );
    }

    public static Task taskInTesting(User creator, User assignee) {
        return simpleTask(TaskStatus.TESTING, creator, assignee);
    }

    public static Task unassignedBacklogTask(User creator) {
        return simpleTask(TaskStatus.BACKLOG, creator, null);
    }

    public static TaskComponent componentEngineering() {
        return new TaskComponent(randomId(), "Engineering", projectDeathStar());
    }

    public static TaskComponent componentLogistics() {
        return new TaskComponent(randomId(), "Imperial Logistics", projectDeathStar());
    }

    public static Project projectDeathStar() {
        return new Project(randomId(), "Death Star");
    }

    public static Project projectWithMembers(User... users) {
        return new Project(randomId(), "Starkiller Base");
    }

    // -------- DTOs --------

    public static CreateTaskRequest createRequestWithAssignee(TaskComponent taskComponent, User assignee) {
        return new CreateTaskRequest(
                "Install hyperdrive",
                "Connect power lines and run diagnostics",
                taskComponent.getId(),
                assignee.getId()
        );
    }

    public static CreateTaskRequest createRequestWithoutAssignee(TaskComponent taskComponent) {
        return new CreateTaskRequest(
                "Calibrate sensors",
                "Use protocol outlined in imperial manual section 3-B",
                taskComponent.getId(),
                null
        );
    }

    public static AssignTaskRequest assignRequestTo(User user) {
        return new AssignTaskRequest(user.getId());
    }

    public static AssignTaskRequest assignRequestTo(UUID userId) {
        return new AssignTaskRequest(userId);
    }

    private static UUID randomId() {
        return UUID.randomUUID();
    }
}

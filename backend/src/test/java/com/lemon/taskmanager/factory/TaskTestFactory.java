package com.lemon.taskmanager.factory;

import com.lemon.taskmanager.tasks.controller.dto.AssignTaskRequest;
import com.lemon.taskmanager.tasks.controller.dto.CreateTaskRequest;
import com.lemon.taskmanager.tasks.domain.TaskComponent;
import com.lemon.taskmanager.tasks.domain.Project;
import com.lemon.taskmanager.tasks.domain.Task;
import com.lemon.taskmanager.tasks.domain.TaskStatus;
import com.lemon.taskmanager.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TaskTestFactory {

    public static Task simpleTask(TaskStatus status, User creator, User assignee) {
        Task task = new Task(
                randomId(),
                "Fix tractor beam",
                "Realign crystal array in Death Star",
                creator,
                assignee,
                componentEngineering(creator)
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
                componentLogistics(creator)
        );
    }

    public static Task taskInTesting(User creator, User assignee) {
        return simpleTask(TaskStatus.TESTING, creator, assignee);
    }

    public static Task unassignedBacklogTask(User creator) {
        return simpleTask(TaskStatus.BACKLOG, creator, null);
    }

    public static TaskComponent componentEngineering(User creator) {
        return new TaskComponent(randomId(), "Engineering", projectDeathStar(creator));
    }

    public static TaskComponent componentLogistics(User creator) {
        return new TaskComponent(randomId(), "Imperial Logistics", projectDeathStar(creator));
    }


    public static Project projectDeathStar(User creator) {
        Project project = new Project(randomId(), "Death Star", creator);
        project.addMember(creator);
        return project;
    }

    public static Project projectWithMembers(User... users) {
        User creator = users.length > 0 ? users[0] : null;
        Project project = new Project(randomId(), "Starkiller Base", creator);

        for (User u : users) {
            project.addMember(u);
        }
        return project;
    }


    // -------- DTOs --------

    public static CreateTaskRequest createRequestWithAssignee(TaskComponent taskComponent, User assignee) {
        return new CreateTaskRequest(
                "Install hyperdrive",
                "Connect power lines and run diagnostics",
//                taskComponent.getId(),
                assignee.getId()
        );
    }

    public static CreateTaskRequest createRequestWithoutAssignee(UUID componentId) {
        return new CreateTaskRequest(
                "Calibrate sensors",
                "Use protocol outlined in imperial manual section 3-B",
//                componentId,
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

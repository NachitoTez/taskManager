package com.lemon.taskmanager.tasks.repository;

import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
}

package com.lemon.taskmanager.tasks.repository;

import com.lemon.taskmanager.tasks.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}

package com.lemon.taskmanager.tasks.repository;

import com.lemon.taskmanager.tasks.repository.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}

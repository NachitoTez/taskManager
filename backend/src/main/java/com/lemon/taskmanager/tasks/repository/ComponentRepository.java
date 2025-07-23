package com.lemon.taskmanager.tasks.repository;

import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ComponentRepository extends CrudRepository<ComponentEntity, Long> {
}

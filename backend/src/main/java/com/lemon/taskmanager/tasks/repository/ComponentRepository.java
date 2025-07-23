package com.lemon.taskmanager.tasks.repository;

import com.lemon.taskmanager.tasks.repository.model.ComponentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComponentRepository extends CrudRepository<ComponentEntity, UUID> {
}

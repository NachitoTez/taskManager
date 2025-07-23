package com.lemon.taskmanager.user.repository;

import com.lemon.taskmanager.user.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(UUID id);

    boolean existsByUsername(String username);
}

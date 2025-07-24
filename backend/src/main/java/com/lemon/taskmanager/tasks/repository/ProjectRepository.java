package com.lemon.taskmanager.tasks.repository;

import com.lemon.taskmanager.tasks.repository.model.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, UUID> {

    @Query("SELECT p FROM ProjectEntity p JOIN p.members m WHERE m.id = :userId")
    List<ProjectEntity> findAllByMemberId(@Param("userId") UUID userId);
}


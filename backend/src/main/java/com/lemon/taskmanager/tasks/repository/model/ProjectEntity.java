package com.lemon.taskmanager.tasks.repository.model;

import com.lemon.taskmanager.user.repository.model.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator_id")
    private UserEntity creator;

    @ManyToMany
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> members = new HashSet<>();


    public ProjectEntity() {}

    public ProjectEntity(UUID id, UserEntity creator, String name) {
        this.id = id;
        this.creator = creator;
        this.name = name;
        this.members.add(creator);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public Set<UserEntity> getMembers() {
        return members;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public void setMembers(Set<UserEntity> members) {
        this.members = members;
    }

    public void addMember(UserEntity user) {
        this.members.add(user);
    }

}

package com.lemon.taskmanager.user.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemon.taskmanager.tasks.domain.Role;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public UserEntity() {}

    public UserEntity(String username, String hashedPassword, Role role) {
        this.username = username;
        this.password = hashedPassword;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEncryptedPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean passwordMatches(String rawPassword, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.password);
    }
}

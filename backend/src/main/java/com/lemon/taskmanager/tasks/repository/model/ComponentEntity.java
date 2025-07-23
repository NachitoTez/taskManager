package com.lemon.taskmanager.tasks.repository.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "components")
public class ComponentEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    //TODO estoy pensando como hago las relaciones entre tarea -> componente -> proyecto
    public ComponentEntity() {}

    public ComponentEntity(String name) {
        this.name = name;
    }

    public ComponentEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

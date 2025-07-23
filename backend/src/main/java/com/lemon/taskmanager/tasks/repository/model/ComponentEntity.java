package com.lemon.taskmanager.tasks.repository.model;

import jakarta.persistence.*;

@Entity
@Table(name = "components")
public class ComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //TODO estoy pensando como hago las relaciones entre tarea -> componente -> proyecto
    public ComponentEntity() {}

    public ComponentEntity(String name) {
        this.name = name;
    }

    public ComponentEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

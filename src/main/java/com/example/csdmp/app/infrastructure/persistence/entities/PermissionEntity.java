package com.example.csdmp.app.infrastructure.persistence.entities;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"resource", "action"})
        }
)
public class PermissionEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String resource;

    @Column(nullable = false)
    private String action;

    private String description;

    public PermissionEntity() {}

    public PermissionEntity(UUID id, String resource, String action, String description) {
        this.id = id;
        this.resource = resource;
        this.action = action;
        this.description = description;
    }

    // Getters et Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getResource() { return resource; }
    public void setResource(String resource) { this.resource = resource; }
    public String getAction() { return action; }
    public void setAAction(String action) { this.action = action; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
}

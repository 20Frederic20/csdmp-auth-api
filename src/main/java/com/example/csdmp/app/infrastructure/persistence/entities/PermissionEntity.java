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
}

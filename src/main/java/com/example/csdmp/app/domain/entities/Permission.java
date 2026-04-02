package com.example.csdmp.app.domain.entities;

import java.util.UUID;

public class Permission {
    private final UUID id;
    private final String resource; // ex: "patients", "bills", "users"
    private final String action;   // ex: "create", "read", "update", "delete"
    private final String description;

    public Permission(UUID id, String resource, String action, String description) {
        this.id = id;
        this.resource = resource;
        this.action = action;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getResource() {
        return resource;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public String getSlug() {
        return resource + ":" + action;
    }
}


package com.example.csdmp.app.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Role {
    private final UUID id;
    private final String name;
    private final String description;
    private final List<Permission> permissions;

    public Role(UUID id, String name, String description, List<Permission> permissions){
            this.id=id;
            this.name=name;
            this.description=description;
            this.permissions = permissions != null ? permissions : new ArrayList<>();
    }
    public List<Permission> getPermissions() { return permissions; }


    public UUID getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getDescription() {
        return description;
    }
}

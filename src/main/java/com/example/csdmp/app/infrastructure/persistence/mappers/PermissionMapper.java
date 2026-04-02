package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.infrastructure.persistence.entities.PermissionEntity;


public class PermissionMapper {
    public static PermissionEntity toEntity(Permission domain) {
        return new PermissionEntity(domain.getId(), domain.getResource(), domain.getAction(), domain.getDescription());
    }

    public static Permission toDomain(PermissionEntity entity) {
        return new Permission(entity.getId(), entity.getResource(), entity.getAction(), entity.getDescription());
    }
}

package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.infrastructure.persistence.entities.RoleEntity;

import java.util.List;

public class RoleMapper {
    public static RoleEntity toEntity(Role domain) {
        return new RoleEntity(domain.getId(), domain.getName(), domain.getDescription());
    }

    public static Role toDomain(RoleEntity entity) {
        List<Permission> domainPermissions = entity.getPermissions().stream()
                .map(PermissionMapper::toDomain)
                .toList();
        return new Role(entity.getId(), entity.getName(), entity.getDescription(), domainPermissions);
    }
}
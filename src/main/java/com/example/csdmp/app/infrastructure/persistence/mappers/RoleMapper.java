package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.infrastructure.persistence.entities.PermissionEntity;
import com.example.csdmp.app.infrastructure.persistence.entities.RoleEntity;

import java.util.List;

public class RoleMapper {
    public static RoleEntity toEntity(Role domain) {
        RoleEntity entity = new RoleEntity(domain.getId(), domain.getName(), domain.getDescription());

        if (domain.getPermissions() != null) {
            List<PermissionEntity> permissionEntities = domain.getPermissions().stream()
                    .map(PermissionMapper::toEntity)
                    .toList();
            entity.setPermissions(permissionEntities);
        }

        return entity;
    }

    public static Role toDomain(RoleEntity entity) {
        List<Permission> domainPermissions = (entity.getPermissions() == null)
                ? List.of()
                : entity.getPermissions().stream()
                  .map(PermissionMapper::toDomain)
                  .toList();

        return new Role(entity.getId(), entity.getName(), entity.getDescription(), domainPermissions);
    }
}
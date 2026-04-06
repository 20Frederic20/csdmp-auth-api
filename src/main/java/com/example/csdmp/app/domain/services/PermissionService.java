package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.dtos.PaginatedResult;
import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.exceptions.BusinessException;
import com.example.csdmp.app.domain.exceptions.EntityNotFoundException;
import com.example.csdmp.app.domain.repositories.PermissionRepository;

import java.util.List;
import java.util.UUID;

public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission create(String resource, String action, String description) {
        if (permissionRepository.findByResourceAndAction(resource, action).isPresent()) {
            throw new BusinessException("La permission existe déjà");
        }
        Permission permission = new Permission(UUID.randomUUID(), resource, action, description);
        permissionRepository.save(permission);
        return permission;
    }

    public PaginatedResult<Permission> getAll(int page, int size) {
        return permissionRepository.findAll(page, size);
    }

    public Permission update(UUID id, String newResource, String newAction, String newDescription) {
        Permission existingRole = permissionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Permission non trouvé avec l'ID : " + id)
        );
        Permission updatedPermission = new Permission(existingRole.getId(), newResource, newAction, newDescription);

        permissionRepository.save(updatedPermission);
        return updatedPermission;
    }

    public void delete(UUID id) {
        if (permissionRepository.findById(id).isEmpty()) {
            throw new BusinessException("Impossible de supprimer : le rôle n'existe pas.");
        }
        permissionRepository.delete(id);
    }
}

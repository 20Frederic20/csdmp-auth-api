package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.repositories.PermissionRepository;
import com.example.csdmp.app.domain.repositories.RoleRepository;


import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(
            RoleRepository roleRepository, PermissionRepository permissionRepository
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public Role create(String name, String description, List<UUID> permissionIds) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Le rôle existe déjà");
        }
        List<Permission> permissions = permissionIds.stream()
                .map(pId -> permissionRepository.findById(pId).orElseThrow())
                .toList();
        Role role = new Role(UUID.randomUUID(), name, description, permissions);
        roleRepository.save(role);
        return role;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role update(UUID id, String newName, String newDescription, List<UUID> newPermissionIds) {
        Role existingRole = roleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Rôle non trouvé avec l'ID : " + id)
        );
        List<Permission> newPermissions = newPermissionIds.stream()
                .map(pId -> permissionRepository.findById(pId).orElseThrow())
                .toList();
        Role updatedRole = new Role(existingRole.getId(), newName, newDescription, newPermissions);

        roleRepository.save(updatedRole);
        return updatedRole;
    }

    public void delete(UUID id) {
        if (roleRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Impossible de supprimer : le rôle n'existe pas.");
        }
        roleRepository.delete(id);
    }
}
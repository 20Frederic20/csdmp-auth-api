package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.repositories.RoleRepository;


import java.util.UUID;
import java.util.List;

public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role create(String name, String description) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Le rôle existe déjà");
        }
        Role role = new Role(UUID.randomUUID(), name, description);
        roleRepository.save(role);
        return role;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role update(UUID id, String newName, String newDescription) {
        Role existingRole = roleRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Rôle non trouvé avec l'ID : " + id)
        );
        Role updatedRole = new Role(existingRole.getId(), newName, newDescription);

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
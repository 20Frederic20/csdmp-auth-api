package com.example.csdmp.app.domain.repositories;

import com.example.csdmp.app.domain.entities.Role;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface RoleRepository {
    void save(Role role);
    Optional<Role> findById(UUID id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
    void delete(UUID id);
}
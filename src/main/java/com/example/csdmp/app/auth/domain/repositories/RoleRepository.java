package com.example.csdmp.app.auth.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import com.example.csdmp.app.auth.domain.entities.Role;

import java.util.List;

public interface RoleRepository {
    void save(Role role);
    Optional<Role> findById(UUID id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
    void delete(UUID id);
}
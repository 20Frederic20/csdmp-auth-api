package com.example.csdmp.app.domain.repositories;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.shared.domain.dtos.PaginatedResult;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    void save(Permission permission);
    Optional<Permission> findById(UUID id);
    Optional<Permission> findByResourceAndAction(String resource, String action);
    PaginatedResult<Permission> findAll(int page, int size);
    void delete(UUID id);
}



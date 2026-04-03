package com.example.csdmp.app.domain.repositories;

import com.example.csdmp.app.domain.dtos.PaginatedResult;
import com.example.csdmp.app.domain.entities.Permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository {
    void save(Permission permission);
    Optional<Permission> findById(UUID id);
    Optional<Permission> findByResourceAndAction(String resource, String action);
    PaginatedResult<Permission> findAll(int page, int size);
    void delete(UUID id);
}



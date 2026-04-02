package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.infrastructure.persistence.entities.PermissionEntity;
import com.example.csdmp.app.infrastructure.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaPermissionRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<PermissionEntity> findBySlug(String resource, String action);
}

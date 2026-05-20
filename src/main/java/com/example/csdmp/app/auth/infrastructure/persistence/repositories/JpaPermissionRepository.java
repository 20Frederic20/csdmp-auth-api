package com.example.csdmp.app.auth.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.csdmp.app.auth.infrastructure.persistence.entities.PermissionEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByResourceAndAction(String resource, String action);
}

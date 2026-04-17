package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.infrastructure.persistence.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaPermissionRepository extends JpaRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByResourceAndAction(String resource, String action);
}

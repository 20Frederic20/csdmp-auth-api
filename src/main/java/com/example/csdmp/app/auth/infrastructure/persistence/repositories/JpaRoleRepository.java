package com.example.csdmp.app.auth.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.csdmp.app.auth.infrastructure.persistence.entities.RoleEntity;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface JpaRoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByName(String name);
}
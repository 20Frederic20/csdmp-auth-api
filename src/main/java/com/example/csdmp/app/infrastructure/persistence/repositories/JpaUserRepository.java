package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String name);
    Optional<UserEntity> findByHealthId(String healthId);
}

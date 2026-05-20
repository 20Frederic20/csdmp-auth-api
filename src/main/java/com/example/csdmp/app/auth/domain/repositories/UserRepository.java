package com.example.csdmp.app.auth.domain.repositories;

import com.example.csdmp.app.auth.domain.entities.User;
import com.example.csdmp.app.shared.domain.dtos.PaginatedResult;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email, boolean includeDeleted);
    Optional<User> findByHealthId(String healthId);
    PaginatedResult<User> findAll(int page, int size);
    void delete(UUID id);
}

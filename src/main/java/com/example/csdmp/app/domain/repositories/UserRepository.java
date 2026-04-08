package com.example.csdmp.app.domain.repositories;

import com.example.csdmp.app.domain.dtos.PaginatedResult;
import com.example.csdmp.app.domain.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByHealthId(String healthId);
    PaginatedResult<User> findAll(int page, int size);
    void delete(UUID id);
}

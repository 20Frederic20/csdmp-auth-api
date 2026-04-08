package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.domain.dtos.PaginatedResult;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.repositories.UserRepository;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import com.example.csdmp.app.infrastructure.persistence.mappers.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaRepository;

    public UserRepositoryImpl(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        jpaRepository.save(UserMapper.toEntity(user));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByHealthId(String healthId) {
        return jpaRepository.findByHealthId(healthId).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public PaginatedResult<User> findAll(int page, int size){
        Page<UserEntity> usersPage = jpaRepository.findAll(PageRequest.of(page, size));
        List<User> users = usersPage.getContent().stream()
                .map(UserMapper::toDomain)
                .toList();

        return new PaginatedResult<>(
                users,
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.getNumber()
        );
    }

    @Override
    public void delete(UUID id){
        jpaRepository.deleteById(id);
    }
}

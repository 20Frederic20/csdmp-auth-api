package com.example.csdmp.app.auth.infrastructure.persistence.repositories;

import com.example.csdmp.app.auth.domain.entities.Permission;
import com.example.csdmp.app.auth.domain.repositories.PermissionRepository;
import com.example.csdmp.app.auth.infrastructure.persistence.entities.PermissionEntity;
import com.example.csdmp.app.auth.infrastructure.persistence.mappers.PermissionMapper;
import com.example.csdmp.app.shared.domain.dtos.PaginatedResult;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {
    private final JpaPermissionRepository jpaPermissionRepository;

    public PermissionRepositoryImpl(JpaPermissionRepository jpaPermissionRepository) {
        this.jpaPermissionRepository = jpaPermissionRepository;
    }

    @Override
    public void save(Permission permission) {
        jpaPermissionRepository.save(PermissionMapper.toEntity(permission));
    }

    @Override
    public Optional<Permission> findById(UUID id) {
        return jpaPermissionRepository.findById(id).map(PermissionMapper::toDomain);
    }

    @Override
    public Optional<Permission> findByResourceAndAction(String resource, String action) {
        return jpaPermissionRepository.findByResourceAndAction(resource, action).map(PermissionMapper::toDomain);
    }

    @Override
    public PaginatedResult<Permission> findAll(int page, int size) {
        Page<PermissionEntity> entityPage = jpaPermissionRepository.findAll(PageRequest.of(page, size));

        List<Permission> domainList = entityPage.getContent().stream()
                .map(PermissionMapper::toDomain)
                .toList();

        return new PaginatedResult<>(
                domainList,
                entityPage.getTotalElements(),
                entityPage.getTotalPages(),
                entityPage.getNumber()
        );
    }

    @Override
    public void delete(UUID id){
        jpaPermissionRepository.deleteById(id);
    }

}

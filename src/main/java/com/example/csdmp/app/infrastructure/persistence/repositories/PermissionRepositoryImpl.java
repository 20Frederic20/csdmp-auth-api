package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.repositories.PermissionRepository;
import com.example.csdmp.app.infrastructure.persistence.mappers.PermissionMapper;
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
    public List<Permission> findAll(){
        return jpaPermissionRepository.findAll().stream().map(PermissionMapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id){
        jpaPermissionRepository.deleteById(id);
    }

}

package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import com.example.csdmp.app.infrastructure.persistence.mappers.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RoleRepositoryImpl implements RoleRepository {
    private final JpaRoleRepository jpaRepository;

    public RoleRepositoryImpl(JpaRoleRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Role role) {
        jpaRepository.save(RoleMapper.toEntity(role));
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRepository.findByName(name).map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return jpaRepository.findById(id).map(RoleMapper::toDomain);
    }

    @Override
    public List<Role>  findAll(){
        return jpaRepository.findAll().stream().map(RoleMapper::toDomain).toList();
    }

    @Override
    public void delete(UUID id){
        jpaRepository.deleteById(id);
    }
}
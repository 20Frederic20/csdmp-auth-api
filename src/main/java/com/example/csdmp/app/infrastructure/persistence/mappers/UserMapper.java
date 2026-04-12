package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.infrastructure.persistence.entities.RoleEntity;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        List<Role> entityRoles = (entity.getRoles() == null)
                ? List.of()
                : entity.getRoles().stream()
                  .map(RoleMapper::toDomain)
                  .toList();
        return new User(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getHealthId(),
                entity.getPassword(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt(),
                entityRoles
        );
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;
        List<RoleEntity> roleEntities = (domain.getRoles() == null)
                ? List.of()
                : domain.getRoles().stream()
                    .map(RoleMapper::toEntity)
                    .toList();
        UserEntity entity = new UserEntity(
                domain.getId(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getHealthId(),
                domain.getIsActive()
        );

        entity.setRoles(roleEntities);
        return entity;
    }
}
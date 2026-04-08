package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final UserMapper userMapper;

    public UserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return new User(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getHealthId(),
                entity.getPassword(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) return null;

        return new UserEntity(
                domain.getId(),
                domain.getFirstName(),
                domain.getLastName(),
                domain.getEmail(),
                domain.getPassword(),
                domain.getHealthId(),
                domain.isActive() // On passe l'état actif au constructeur
        );
    }
}
package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserMapper() {
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
                entity.getIsActive(),
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
                domain.getIsActive() // On passe l'état actif au constructeur
        );
    }
}
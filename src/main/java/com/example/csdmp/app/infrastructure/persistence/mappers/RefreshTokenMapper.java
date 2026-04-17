package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.RefreshToken;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.infrastructure.persistence.entities.RefreshTokenEntity;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;

public class RefreshTokenMapper {
    public static RefreshTokenEntity toEntity(RefreshToken token) {
        UserEntity userEntity = UserMapper.toEntity(token.getUser());
        return  new RefreshTokenEntity(token.getId(), token.getToken(), userEntity, token.getExpiryDate(), token.isRevoked());
    }

    public static RefreshToken toDomain(RefreshTokenEntity token) {
        User user = UserMapper.toDomain(token.getUser());
        return  new RefreshToken(token.getId(), token.getToken(), user, token.getExpiryDate(), token.isRevoked());
    }
}

package com.example.csdmp.app.auth.domain.repositories;

import com.example.csdmp.app.auth.domain.entities.RefreshToken;
import com.example.csdmp.app.auth.infrastructure.persistence.entities.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    Optional<RefreshToken> findByToken(String token);
    void revokeAllUserTokens(UUID userId);
    void deleteByToken(String token);
    void save(RefreshTokenEntity refreshToken);
}

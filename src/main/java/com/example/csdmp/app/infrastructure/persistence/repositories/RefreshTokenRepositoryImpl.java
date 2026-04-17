package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.domain.entities.RefreshToken;
import com.example.csdmp.app.domain.repositories.RefreshTokenRepository;
import com.example.csdmp.app.infrastructure.persistence.entities.RefreshTokenEntity;
import com.example.csdmp.app.infrastructure.persistence.mappers.RefreshTokenMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final JpaRefreshTokenRepository JpaRefreshTokenRepository;

    public RefreshTokenRepositoryImpl(JpaRefreshTokenRepository JpaRefreshTokenRepository) {
        this.JpaRefreshTokenRepository = JpaRefreshTokenRepository;
    }

    @Override
    public void save(RefreshTokenEntity refreshToken) {
        this.JpaRefreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return this.JpaRefreshTokenRepository.findByToken(token).map(RefreshTokenMapper::toDomain);
    }

    @Override
    public void revokeAllUserTokens(UUID userId){
        this.JpaRefreshTokenRepository.revokeAllUserTokens(userId);
    }

    @Override
    public void deleteByToken(String token){
        this.JpaRefreshTokenRepository.deleteByToken(token);
    }
}

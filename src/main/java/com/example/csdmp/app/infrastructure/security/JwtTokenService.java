package com.example.csdmp.app.infrastructure.security;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.repositories.RefreshTokenRepository;
import com.example.csdmp.app.domain.security.TokenInterface;
import com.example.csdmp.app.infrastructure.persistence.entities.RefreshTokenEntity;
import com.example.csdmp.app.infrastructure.persistence.mappers.UserMapper;
import com.example.csdmp.app.infrastructure.persistence.repositories.JpaRefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtTokenService implements TokenInterface {
    private final RefreshTokenRepository refreshTokenRepository;
    private  final JpaRefreshTokenRepository jpaRefreshTokenRepository;
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public JwtTokenService(RefreshTokenRepository refreshTokenRepository, JpaRefreshTokenRepository jpaRefreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
        this.jpaRefreshTokenRepository = jpaRefreshTokenRepository;
    }

    @Override
    public String generateAccessToken(@NonNull User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream().map(Role::getName).toList());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getHealthId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @Override
    @Transactional
    public String generateRefreshToken(User user) {
        refreshTokenRepository.revokeAllUserTokens(user.getId());
        String token = UUID.randomUUID().toString();

        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setId(UUID.randomUUID());
        refreshToken.setUser(UserMapper.toEntity(user));
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Override
    public User validateRefreshToken(String token) {
        return null;
    }

    @Override
    public void revokeRefreshToken(String token) {
        int i = jpaRefreshTokenRepository.revokeByToken(token);
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        try {
            final String healthId = extractHealthId(token);
            return (healthId.equals(user.getHealthId()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey()) // Utilise la clé de ton application.properties
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractHealthId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

package com.example.csdmp.app.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshToken {
    private final UUID id;
    private final String token;
    private final User user;
    private final LocalDateTime expiryDate;
    private final boolean revoked;

    public RefreshToken(
            UUID id,
            String token,
            User user,
            LocalDateTime expiryDate,
            boolean revoked
    ) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
    }

    public UUID getId() {
        return this.id;
    }

    public String getToken(){
        return this.token;
    }

    public User getUser(){
        return this.user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public boolean isRevoked() {
        return revoked;
    }
}

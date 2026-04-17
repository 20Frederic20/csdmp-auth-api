package com.example.csdmp.app.domain.security;

import com.example.csdmp.app.domain.entities.User;

public interface TokenInterface {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    User validateRefreshToken(String token);
    void revokeRefreshToken(String token);
    String extractHealthId(String token);
    boolean isTokenValid(String token, User user);
}

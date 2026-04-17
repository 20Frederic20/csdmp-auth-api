package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.exceptions.UnauthorizedException;
import com.example.csdmp.app.domain.repositories.UserRepository;
import com.example.csdmp.app.domain.security.PasswordInterface;
import com.example.csdmp.app.domain.security.TokenInterface;
import com.example.csdmp.app.interfaces.rest.dtos.AuthResponse;

import java.util.List;
import java.util.UUID;

public class AuthService {
    private final UserRepository userRepository;
    private final PasswordInterface passwordInterface;
    private final TokenInterface tokenInterface;

    public AuthService(UserRepository userRepository, PasswordInterface passwordInterface, TokenInterface tokenInterface) {
        this.userRepository = userRepository;
        this.passwordInterface = passwordInterface;
        this.tokenInterface = tokenInterface;
    }

    public AuthResponse register(String firstName, String lastName, String email, String healthId, String password, List<UUID> roleIds, boolean isActive) {
        UserService userService = new UserService(userRepository, null, passwordInterface);
        User user = userService.create(firstName, lastName, email, healthId, password, roleIds, isActive);
        String accessToken = this.tokenInterface.generateAccessToken(user);
        String refreshToken = this.tokenInterface.generateRefreshToken(user);
        return new AuthResponse(accessToken,refreshToken);
    }

    public AuthResponse login(String healthId, String password) {
        User user = userRepository.findByHealthId(healthId)
                .filter(u -> passwordInterface.verify(password, u.getPassword()))
                .orElseThrow(() -> new UnauthorizedException("Identifiants non valides"));

        String accessToken = this.tokenInterface.generateAccessToken(user);
        String refreshToken = this.tokenInterface.generateRefreshToken(user);
        return new AuthResponse(accessToken,refreshToken);
    }
}

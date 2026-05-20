package com.example.csdmp.app.auth.infrastructure.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.csdmp.app.auth.domain.security.PasswordInterface;

@Component
public class BCryptPasswordHasher implements  PasswordInterface{
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String password) {
        return encoder.encode(password);
    }

    @Override
    public boolean verify(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}

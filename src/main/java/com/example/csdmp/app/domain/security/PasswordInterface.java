package com.example.csdmp.app.domain.security;

public interface PasswordInterface {
    String hash(String password);
    boolean verify(String rawPassword, String hashedPassword);
}

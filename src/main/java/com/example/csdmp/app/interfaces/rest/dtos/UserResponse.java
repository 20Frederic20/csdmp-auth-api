package com.example.csdmp.app.interfaces.rest.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(UUID id, String firstName, String lastName, String email, String healthId, String password, boolean isActive, LocalDateTime createdAT, LocalDateTime updatedAt, LocalDateTime deletedAt) {
}

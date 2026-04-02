package com.example.csdmp.app.interfaces.rest.dtos;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        String description
) {}
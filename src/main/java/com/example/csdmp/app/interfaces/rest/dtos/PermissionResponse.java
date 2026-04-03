package com.example.csdmp.app.interfaces.rest.dtos;

import java.util.UUID;

public record PermissionResponse(
        UUID id,
        String resource,
        String action,
        String description
) {
}

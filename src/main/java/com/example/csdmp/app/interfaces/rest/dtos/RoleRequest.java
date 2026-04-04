package com.example.csdmp.app.interfaces.rest.dtos;

import java.util.List;
import java.util.UUID;

public record RoleRequest(String name, String description, List<UUID> permissionIds) {}
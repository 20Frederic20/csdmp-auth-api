package com.example.csdmp.app.auth.interfaces.rest.dtos;

public record PermissionRequest(String resource, String action, String description) {
}

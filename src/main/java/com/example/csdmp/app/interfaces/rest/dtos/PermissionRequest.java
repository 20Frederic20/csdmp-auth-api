package com.example.csdmp.app.interfaces.rest.dtos;

public record PermissionRequest(String resource, String action, String description) {
}

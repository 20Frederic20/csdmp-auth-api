package com.example.csdmp.app.interfaces.rest.dtos;

import java.util.List;
import java.util.UUID;

public record UserRequest(String firstName, String lastName, String email, String healthId, String password, List<UUID> roleIds, boolean isActive) {
}

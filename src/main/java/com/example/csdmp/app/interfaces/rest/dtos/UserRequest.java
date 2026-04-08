package com.example.csdmp.app.interfaces.rest.dtos;

import java.util.UUID;

public record UserRequest(String firstName, String lastName, String email, String healthId, String password, boolean isActive) {
}

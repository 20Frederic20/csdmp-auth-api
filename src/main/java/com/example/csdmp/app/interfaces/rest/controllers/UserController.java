package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.services.RoleService;
import com.example.csdmp.app.domain.services.UserService;
import com.example.csdmp.app.interfaces.rest.docs.ApiMutationErrors;
import com.example.csdmp.app.interfaces.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Créer un nouveau rôle", description = "Permet d'associer un nom à une liste de permissions")
    @ApiResponse(responseCode = "201", description = "Rôle créé avec succès")
    @ApiMutationErrors
    @PostMapping()
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        User created = userService.create(request.firstName(), request.lastName(), request.email(), request.healthId(), request.password(), request.roleIds(), request.isActive());
        return ResponseEntity.ok(new UserResponse(created.getId(), created.getFirstName(), created.getLastName(), created.getEmail(), created.getHealthId(), created.getPassword(), created.getIsActive(), created.getCreatedAt(), created.getUpdatedAt(), created.getDeletedAt()));
    }
}

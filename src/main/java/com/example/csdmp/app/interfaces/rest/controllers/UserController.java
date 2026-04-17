package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.services.UserService;
import com.example.csdmp.app.interfaces.rest.docs.ApiMutationErrors;
import com.example.csdmp.app.interfaces.rest.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Créer un nouveau rôle", description = "Permet d'associer un nom à une liste de permissions")
    @ApiResponse(responseCode = "201", description = "Rôle créé avec succès")
    @ApiMutationErrors
    @PostMapping("")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
        User created = userService.create(request.firstName(), request.lastName(), request.email(), request.healthId(), request.password(), request.roleIds(), request.isActive());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return getCreatedUserResponseResponseEntity(location, created);
    }

    @Operation(summary = "Modifier un utilisateur", description = "Modifier les informations de l'utilisatuer")
    @ApiResponse(responseCode = "200", description="Utilisateur mis à jour avec succès")
    @ApiMutationErrors
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UserRequest request) {
        User updated = userService.update(id, request.firstName(), request.lastName(), request.email(), request.healthId(), request.password(), request.roleIds(), request.isActive());
        return getUserResponseResponseEntity(updated);
    }

    @Operation(summary = "Trouver un utilisateur", description = "Trouver les informations de l'utilisatuer")
    @ApiResponse(responseCode = "200", description="Utilisateur mis à jour avec succès")
    @ApiMutationErrors
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        User user = userService.findById(id);
        return getUserResponseResponseEntity(user);
    }

    @NonNull
    private ResponseEntity<UserResponse> getUserResponseResponseEntity(User created) {
        return ResponseEntity.ok(new UserResponse(created.getId(), created.getFirstName(), created.getLastName(), created.getEmail(), created.getHealthId(), created.getPassword(), created.getRoles().stream().map(
                role -> new RoleResponse(role.getId(), role.getName(), role.getDescription(), List.of())
        ).toList(), created.getIsActive(), created.getCreatedAt(), created.getUpdatedAt(), created.getDeletedAt()));
    }

    @NonNull
    private ResponseEntity<UserResponse> getCreatedUserResponseResponseEntity(URI location, User created) {
        return ResponseEntity.created(location).body(new UserResponse(created.getId(), created.getFirstName(), created.getLastName(), created.getEmail(), created.getHealthId(), created.getPassword(), created.getRoles().stream().map(
                role -> new RoleResponse(role.getId(), role.getName(), role.getDescription(), List.of())
        ).toList(), created.getIsActive(), created.getCreatedAt(), created.getUpdatedAt(), created.getDeletedAt()));
    }
}

package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.services.RoleService;
import com.example.csdmp.app.interfaces.rest.dtos.PermissionResponse;
import com.example.csdmp.app.interfaces.rest.dtos.RoleRequest;
import com.example.csdmp.app.interfaces.rest.dtos.RoleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Gestion des rôles et permissions du système")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<RoleResponse>> all() {
        List<Role> roles = roleService.getAll();

        List<RoleResponse> response = roles.stream()
                .map(role -> new RoleResponse(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getPermissions().stream()
                                .map(p -> new PermissionResponse(p.getId(), p.getResource(), p.getAction(), p.getDescription()))
                                .toList()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Créer un nouveau rôle", description = "Permet d'associer un nom à une liste de permissions")
    @ApiResponse(responseCode = "201", description = "Rôle créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides ou rôle déjà existant")
    @PostMapping()
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        Role created = roleService.create(request.name(), request.description(), request.permissionIds());
        return ResponseEntity.ok(new RoleResponse(created.getId(), created.getName(), created.getDescription(), created.getPermissions().stream()
                .map(p -> new PermissionResponse(p.getId(), p.getResource(), p.getAction(), p.getDescription()))
                .toList()));
    }

    @ApiResponse(responseCode = "200", description = "Rôle mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Role non existant")
    @ApiResponse(responseCode = "400", description = "Données invalides ou rôle déjà existant")
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable UUID id, @RequestBody RoleRequest request) {
        Role updated = roleService.update(id, request.name(), request.description(), request.permissionIds());
        return ResponseEntity.ok(new RoleResponse(updated.getId(), updated.getName(), updated.getDescription(), updated.getPermissions().stream()
                .map(p -> new PermissionResponse(p.getId(), p.getResource(), p.getAction(), p.getDescription()))
                .toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
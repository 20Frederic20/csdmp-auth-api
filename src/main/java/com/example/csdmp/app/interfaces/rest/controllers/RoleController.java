package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.services.RoleService;
import com.example.csdmp.app.interfaces.rest.dtos.RoleRequest;
import com.example.csdmp.app.interfaces.rest.dtos.RoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
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
                        role.getDescription()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        Role created = roleService.create(request.name(), request.description());
        return ResponseEntity.ok(new RoleResponse(created.getId(), created.getName(), created.getDescription()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable UUID id, @RequestBody Role request) {
        Role updated = roleService.update(id, request.getName(), request.getDescription());
        return ResponseEntity.ok(new RoleResponse(updated.getId(), updated.getName(), updated.getDescription()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
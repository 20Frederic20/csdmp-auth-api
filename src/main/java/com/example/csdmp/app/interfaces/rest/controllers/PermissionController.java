package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.services.PermissionService;
import com.example.csdmp.app.interfaces.rest.dtos.PermissionRequest;
import com.example.csdmp.app.interfaces.rest.dtos.PermissionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping()
    public ResponseEntity<List<PermissionResponse>> all() {
        List<Permission> permissions = permissionService.getAll();

        List<PermissionResponse> response = permissions.stream()
                .map(permission -> new PermissionResponse(
                        permission.getId(),
                        permission.getResource(),
                        permission.getAction(),
                        permission.getDescription()
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionRequest request) {
        Permission created = permissionService.create(request.resource(), request.action(), request.description());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(
                new PermissionResponse(
                        created.getId(),
                        created.getResource(),
                        created.getAction(),
                        created.getDescription()
                )
        );
    }


}

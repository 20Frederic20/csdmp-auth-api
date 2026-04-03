package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.services.PermissionService;
import com.example.csdmp.app.interfaces.rest.dtos.PermissionRequest;
import com.example.csdmp.app.interfaces.rest.dtos.PermissionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping()
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionRequest request) {
        Permission created = permissionService.create(request.resource(), request.action(), request.description());
        return ResponseEntity.ok(new PermissionResponse(created.getId(), created.getResource(), created.getAction(), created.getDescription()));
    }
}

package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.dtos.PaginatedResult;
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
    public ResponseEntity<PaginatedResult<PermissionResponse>> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PaginatedResult<Permission> result = permissionService.getAll(page, size);

        // On mappe les entités de domaine vers les DTOs de réponse
        List<PermissionResponse> dtos = result.data().stream()
                .map(p -> new PermissionResponse(p.getId(), p.getResource(), p.getAction(), p.getDescription()))
                .toList();

        // On renvoie l'objet paginé complet
        return ResponseEntity.ok(new PaginatedResult<>(
                dtos,
                result.totalElements(),
                result.totalPages(),
                result.currentPage()
        ));
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

package com.example.csdmp.app.interfaces.rest.controllers;

import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.services.AuthService;
import com.example.csdmp.app.interfaces.rest.docs.ApiMutationErrors;
import com.example.csdmp.app.interfaces.rest.dtos.AuthResponse;
import com.example.csdmp.app.interfaces.rest.dtos.LoginRequest;
import com.example.csdmp.app.interfaces.rest.dtos.UserRequest;
import com.example.csdmp.app.interfaces.rest.dtos.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Créer un nouveau utilisateur", description = "Créer un nouvel utiilisateur dans le système")
    @ApiResponse(responseCode = "201", description = "Rôle créé avec succès")
    @ApiMutationErrors
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request) {
        AuthResponse response = authService.register(request.firstName(), request.lastName(), request.email(), request.healthId(), request.password(), request.roleIds(), request.isActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Se connecter", description = "L'utilisateur tente de se connecter à la plateforme")
    @ApiResponse(responseCode = "200", description="Utilisateur mis à jour avec succès")
    @ApiMutationErrors
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(
                request.healthId(),
                request.password()
        );
        return ResponseEntity.ok(response);
    }
}

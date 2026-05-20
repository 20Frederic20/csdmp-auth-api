package com.example.csdmp.app.auth.interfaces.rest.dtos;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 255, message = "Le prénom ne doit pas dépasser 255 caractères")
    String firstName,

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 255, message = "Le nom ne doit pas dépasser 255 caractères")
    String lastName,

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 255, message = "L'email ne doit pas dépasser 255 caractères")
    String email,

    @NotBlank(message = "L'ID de santé est obligatoire")
    @Size(min = 11, max = 13, message = "L'ID de santé doit contenir exactement 11 à 13 caractères")
    String healthId,

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    String password,


    List<UUID> roleIds,
    boolean isActive
) {
}

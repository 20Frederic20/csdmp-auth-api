package com.example.csdmp.app.auth.interfaces.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @NotBlank(message = "Le NPI est obligatoire")
    @Size(min = 11, max = 13, message = "Le NPI doit contenir exactement 11 à 13 caractères")
    String healthId,
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    String password
) {
}

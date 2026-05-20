package com.example.csdmp.app.auth.interfaces.rest.dtos;

public record AuthResponse(
        String accessToken,
        String refreshToken

) {

}

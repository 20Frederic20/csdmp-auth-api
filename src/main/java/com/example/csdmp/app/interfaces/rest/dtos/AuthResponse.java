package com.example.csdmp.app.interfaces.rest.dtos;

public record AuthResponse(
        String accessToken,
        String refreshToken

) {

}

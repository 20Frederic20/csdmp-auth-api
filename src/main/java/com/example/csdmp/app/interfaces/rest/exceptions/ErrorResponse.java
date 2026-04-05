package com.example.csdmp.app.interfaces.rest.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timestamp) {}

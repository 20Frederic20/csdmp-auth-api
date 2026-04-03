package com.example.csdmp.app.domain.dtos;

import java.util.List;

public record PaginatedResult<T>(
        List<T> data,
        long totalElements,
        int totalPages,
        int currentPage
) {}

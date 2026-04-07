package com.example.csdmp.app.domain.entities;

import java.time.LocalDateTime;

public abstract class BaseDomainEntity {
    public final boolean isActive;
    protected final LocalDateTime createdAt;
    protected final LocalDateTime updatedAt;
    protected final LocalDateTime deletedAt;

    protected BaseDomainEntity(boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.isActive=isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    // Getters

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
}
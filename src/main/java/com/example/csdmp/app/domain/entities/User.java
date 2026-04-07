package com.example.csdmp.app.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class User extends BaseDomainEntity {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String healthId;
    private final String password;

    public User(UUID id, String firstName, String lastName, String email, String healthId, String password, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, boolean isActive) {
        super(isActive, createdAt, updatedAt, deletedAt);
        this.id=id;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.healthId=healthId;
        this.password=password;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHealthId() {
        return healthId;
    }

    public String getPassword() {
        return password;
    }
}

package com.example.csdmp.app.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User extends BaseDomainEntity {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String healthId;
    private final String password;
    private final List<Role> roles;

    public User(UUID id, String firstName, String lastName, String email, String healthId, String password, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, List<Role> roles) {
        super(isActive, createdAt, updatedAt, deletedAt);
        this.id=id;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.healthId=healthId;
        this.password=password;
        this.roles=roles != null ? roles : new ArrayList<>();
    }

    public User(UUID id, String firstName, String lastName, String email, String healthId, String password, List<Role> roles, boolean isActive) {
        this(id, firstName, lastName, email, healthId, password, isActive, LocalDateTime.now(), LocalDateTime.now(), null, roles);
    }

    public UUID getId() {
        return id;
    }

    public List<Role> getRoles() {
        return roles;
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

package com.example.csdmp.app.infrastructure.persistence.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
/** * SQLDelete : On intercepte l'appel à repository.delete()
 * pour faire un UPDATE à la place du DELETE physique.
 */
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
/** * SQLRestriction : Remplace @Where dans Hibernate 6.
 * Filtre automatiquement pour ne garder que les records actifs (NULL).
 */
@SQLRestriction("deleted_at IS NULL")
public class UserEntity extends BaseEntity {

    @Id
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "health_id", unique = true)
    private String healthId;

    @Column(nullable = false)
    private String password;

    /**
     * Relation avec les rôles.
     * EAGER car pour la sécurité, on a souvent besoin des rôles immédiatement.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleEntity> roles = new ArrayList<>();

    public UserEntity(UUID id, String firstName, String lastName, String email, String password, String healthId, boolean active) {
        this.email=email;
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.password=password;
        this.healthId=healthId;
        this.setIsActive(active);
    }

    // --- Getters et Setters ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHealthId() { return healthId; }
    public void setHealthId(String healthId) { this.healthId = healthId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<RoleEntity> getRoles() { return roles; }
    public void setRoles(List<RoleEntity> roles) { this.roles = roles; }
}
package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.entities.Permission;
import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.exceptions.BusinessException;
import com.example.csdmp.app.domain.exceptions.EntityNotFoundException;
import com.example.csdmp.app.domain.repositories.PermissionRepository;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Active Mockito pour JUnit 5
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleService roleService; // Injecte automatiquement les mocks ci-dessus

    private UUID permissionId;
    private Permission mockPermission;

    @BeforeEach
    void setUp() {
        permissionId = UUID.randomUUID();
        mockPermission = new Permission(permissionId, "patients", "create", "Can create patients");
    }

    @Test
    void should_create_role_successfully() {
        // Arrange (On prépare les données et les comportements des mocks)
        String name = "DOCTOR";
        String description = "Medical staff";
        List<UUID> permissionIds = List.of(permissionId);

        when(roleRepository.findByName(name)).thenReturn(Optional.empty()); // Le rôle n'existe pas encore
        when(permissionRepository.findById(permissionId)).thenReturn(Optional.of(mockPermission));

        // Act (On exécute la méthode à tester)
        Role result = roleService.create(name, description, permissionIds);

        // Assert (On vérifie le résultat)
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getPermissions()).hasSize(1);
        assertThat(result.getPermissions().get(0).getResource()).isEqualTo("patients");

        // On vérifie que la méthode save a bien été appelée une fois
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void should_throw_exception_when_role_name_already_exists() {
        String name = "ADMIN";
        when(roleRepository.findByName(name)).thenReturn(Optional.of(new Role(UUID.randomUUID(), name, "desc", List.of())));

        assertThatThrownBy(() -> roleService.create(name, "desc", List.of()))
                .isInstanceOf(BusinessException.class)
                .hasMessage("Le rôle existe déjà");

        // On vérifie que save n'a jamais été appelé car l'exception a coupé le flux
        verify(roleRepository, never()).save(any());
    }

    @Test
    void should_throw_exception_when_permission_not_found() {
        // Arrange
        String name = "NEW_ROLE";
        UUID fakeId = UUID.randomUUID();
        when(roleRepository.findByName(name)).thenReturn(Optional.empty());
        when(permissionRepository.findById(fakeId)).thenReturn(Optional.empty()); // Permission introuvable

        // Act & Assert
        assertThatThrownBy(() -> roleService.create(name, "desc", List.of(fakeId)))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("n'existe pas");
    }
}
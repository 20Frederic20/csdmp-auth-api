package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.exceptions.BusinessException;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import com.example.csdmp.app.domain.repositories.UserRepository;
import com.example.csdmp.app.domain.security.PasswordInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordInterface passwordInterface;

    private UUID roleId;
    private Role mockRole;

    @BeforeEach
    void setUp() {
        roleId = UUID.randomUUID();
        mockRole = new Role(roleId, "PATIENT", "", List.of());
    }

    @Test
    void should_create_user_successfully() {
        String firstName = "Nevis";
        String lastName = "KOULOGBO";
        String email = "levisnevis44@gmail.com";
        String healthId = "012345678910";
        String rawPassword = "secret12345";
        String hashedPassword = "hashed_password";
        boolean isActive = true;
        List<UUID> roleIds = List.of(roleId);

        when(passwordInterface.hash(rawPassword)).thenReturn(hashedPassword);
        when(userRepository.findByHealthId(healthId)).thenReturn(Optional.empty());
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(mockRole));

        User result = userService.create(firstName, lastName, email, healthId, rawPassword, roleIds, isActive);

        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isEqualTo(hashedPassword);
        assertThat(result.getHealthId()).isEqualTo(healthId);
        assertThat(result.getRoles()).hasSize(1);
        assertThat(result.getRoles().get(0).getName()).isEqualTo("PATIENT");

        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void should_throw_exception_if_health_id_is_already_used() {
        String firstName = "Nevis";
        String lastName = "KOULOGBO";
        String email = "levisnevis44@gmail.com";
        String healthId = "012345678910";
        String rawPassword = "secret12345";
        boolean isActive = true;
        List<Role> roles = List.of(mockRole);
        List<UUID> roleIds = List.of(roleId);

        when(userRepository.findByHealthId(healthId)).thenReturn(Optional.of(new User(UUID.randomUUID(), firstName, lastName, email, healthId, rawPassword, roles, isActive)));

        assertThatThrownBy(() -> userService.create(firstName, lastName, email, healthId, rawPassword, roleIds, isActive)).isInstanceOf(BusinessException.class).hasMessage("Un utilisateur avec le mêm numéro CNSS existe déjà");
    }
}

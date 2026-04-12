package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.security.PasswordInterface;
import com.example.csdmp.app.infrastructure.persistence.entities.RoleEntity;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import com.example.csdmp.app.infrastructure.security.BCryptPasswordHasher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
@Import({RoleRepositoryImpl.class, UserRepositoryImpl.class, BCryptPasswordHasher.class})
public class UserRepositoryIT {

    @Autowired
    private JpaRoleRepository roleRepository;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private PasswordInterface passwordInterface;

    @Test
    void should_save_user_in_database() {
        UUID userId = UUID.randomUUID();
        String hashedPassword = passwordInterface.hash("secret12345");
        List <Role> roles = List.of();
        User user = new User(userId, "Nevis", "KOULOGBO", "lewisnevis44@gmail.com", "012345678910", hashedPassword, roles, true);

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail("lewisnevis44@gmail.com", false);

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getPassword()).isEqualTo(hashedPassword);
    }

    @Test
    void should_save_user_with_role_in_database() {
        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        String hashedPassword = passwordInterface.hash("secret12345");
        RoleEntity roleEntity = new RoleEntity(roleId, "PATIENT", "");
        roleRepository.save(roleEntity);
        Role role = new Role(roleId, "PATIENT", "", List.of());
        List <Role> roles = List.of(role);
        User user = new User(userId, "Nevis", "KOULOGBO", "lewisnevis44@gmail.com", "012345678910", hashedPassword, roles, true);

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail("lewisnevis44@gmail.com", false);

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getPassword()).isEqualTo(hashedPassword);
        assertThat(savedUser.get().getRoles())
                .extracting(Role::getName)
                .containsExactly("PATIENT");
    }

    @Test
    void should_delete_user_in_database() {
        UUID userId = UUID.randomUUID();
        String hashedPassword = passwordInterface.hash("secret12345");
        List <Role> roles = List.of();
        User user = new User(userId, "Nevis", "KOULOGBO", "lewisnevis44@gmail.com", "012345678910", hashedPassword, roles, true);

        userRepository.save(user);

        userRepository.delete(userId);

        Optional<User> deletedUser = userRepository.findByEmail("lewisnevis44@gmail.com", true);
        assertThat(deletedUser).isPresent();
        assertThat(deletedUser.get().getDeletedAt())
                .isCloseTo(LocalDateTime.now(), within(5, SECONDS));
    }
}

package com.example.csdmp.app.infrastructure.persistence.mappers;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.infrastructure.persistence.entities.RoleEntity;
import com.example.csdmp.app.infrastructure.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void should_map_domain_user_to_entity() {
        UUID userId = UUID.randomUUID();
        Role role = new Role(UUID.randomUUID(), "ADMIN", "Description", List.of());
        User domainUser = new User(
                userId, "Nevis", "K.", "nevis@test.com", "012345", "hashed_pass", List.of(role), true
        );

        UserEntity entity = UserMapper.toEntity(domainUser);

        assertThat(entity.getId()).isEqualTo(domainUser.getId());
        assertThat(entity.getFirstName()).isEqualTo(domainUser.getFirstName());
        assertThat(entity.getEmail()).isEqualTo(domainUser.getEmail());
        assertThat(entity.getRoles()).hasSize(1);
        assertThat(entity.getRoles().get(0).getName()).isEqualTo("ADMIN");
    }

    @Test
    void should_map_entity_to_domain_user() {
        UUID entityId = UUID.randomUUID();
        UserEntity entity = new UserEntity(
                entityId, "Nevis", "K.", "nevis@test.com", "pass", "H01", true
        );
        List<RoleEntity> roles = List.of(
                new RoleEntity(UUID.randomUUID(), "ADMIN", ""),
                new RoleEntity(UUID.randomUUID(), "SUPERADMIN", "")
        );

        LocalDateTime deletionDate = LocalDateTime.now();
        entity.setDeletedAt(deletionDate);
        entity.setRoles(roles);

        User domainUser = UserMapper.toDomain(entity);


        assertThat(domainUser.getId()).isEqualTo(entityId);
        assertThat(domainUser.getFirstName()).isEqualTo("Nevis");
        assertThat(domainUser.getDeletedAt()).isNotNull();
        assertThat(domainUser.getDeletedAt()).isEqualTo(deletionDate);
        assertThat(domainUser.getRoles())
                .extracting(Role::getName)
                .containsExactly("ADMIN", "SUPERADMIN");
    }
}

    package com.example.csdmp.app.infrastructure.persistence.repositories;

    import com.example.csdmp.app.domain.entities.Permission;
    import com.example.csdmp.app.domain.entities.Role;
    import com.example.csdmp.app.infrastructure.persistence.entities.PermissionEntity;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
    import org.springframework.context.annotation.Import;

    import java.util.List;
    import java.util.Optional;
    import java.util.UUID;

    import static org.assertj.core.api.Assertions.assertThat;

    @DataJpaTest
    @Import({RoleRepositoryImpl.class, PermissionRepositoryImpl.class})
    class RoleRepositoryIT {

        @Autowired
        private RoleRepositoryImpl roleRepository;

        @Autowired
        private JpaPermissionRepository jpaPermissionRepository;

        @Test
        void should_save_role_with_permissions_in_database() {
            UUID permId = UUID.randomUUID();
            PermissionEntity permEntity = new PermissionEntity(permId, "patients", "create", "Desc");
            jpaPermissionRepository.save(permEntity);

            Permission domainPerm = new Permission(permId, "patients", "create", "Desc");
            Role role = new Role(UUID.randomUUID(), "ADMIN", "Super Admin", List.of(domainPerm));

            roleRepository.save(role);

            Optional<Role> savedRole = roleRepository.findByName("ADMIN");

            assertThat(savedRole).isPresent();
            assertThat(savedRole.get().getName()).isEqualTo("ADMIN");

            assertThat(savedRole.get().getPermissions()).hasSize(1);
            assertThat(
                    savedRole.get().getPermissions().get(0).getResource() + ":" + savedRole.get().getPermissions().get(0).getAction()
            ).isEqualTo("patients:create");
        }
    }
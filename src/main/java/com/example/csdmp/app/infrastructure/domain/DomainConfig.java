package com.example.csdmp.app.infrastructure.domain;

import com.example.csdmp.app.domain.repositories.PermissionRepository;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import com.example.csdmp.app.domain.services.PermissionService;
import com.example.csdmp.app.domain.services.RoleService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CSDMP - API de Gestion Hospitalière")
                        .version("1.0")
                        .description("Documentation des endpoints pour la gestion des patients, rôles et utilisateurs au Bénin.")
                        .contact(new Contact().name("Équipe Technique CSDMP")));
    }

    @Bean
    public RoleService roleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        return new RoleService(roleRepository, permissionRepository);
    }

    @Bean
    public PermissionService permmissionService(PermissionRepository permissionRepository) {
        return new PermissionService(permissionRepository);
    }
}

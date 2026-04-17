package com.example.csdmp.app.infrastructure.domain;

import com.example.csdmp.app.domain.repositories.PermissionRepository;
import com.example.csdmp.app.domain.repositories.RefreshTokenRepository;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import com.example.csdmp.app.domain.repositories.UserRepository;
import com.example.csdmp.app.domain.security.PasswordInterface;
import com.example.csdmp.app.domain.security.TokenInterface;
import com.example.csdmp.app.domain.services.AuthService;
import com.example.csdmp.app.domain.services.PermissionService;
import com.example.csdmp.app.domain.services.RoleService;
import com.example.csdmp.app.domain.services.UserService;
import com.example.csdmp.app.infrastructure.persistence.repositories.JpaRefreshTokenRepository;
import com.example.csdmp.app.infrastructure.security.JwtTokenService;
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
    public PermissionService permissionService(PermissionRepository permissionRepository) {
        return new PermissionService(permissionRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository, RoleRepository roleRepository, PasswordInterface passwordInterface) {
        return new UserService(userRepository, roleRepository, passwordInterface);
    }

    @Bean
    public AuthService authService(UserRepository userRepository, PasswordInterface passwordInterface, TokenInterface tokenInterface) {
        return  new AuthService(userRepository, passwordInterface, tokenInterface);
    }
}

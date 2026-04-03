package com.example.csdmp.app.infrastructure.domain;

import com.example.csdmp.app.domain.repositories.PermissionRepository;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import com.example.csdmp.app.domain.services.PermissionService;
import com.example.csdmp.app.domain.services.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Bean
    public RoleService roleService(RoleRepository roleRepository) {
        return new RoleService(roleRepository);
    }

    @Bean
    public PermissionService permmissionService(PermissionRepository permissionRepository) {
        return new PermissionService(permissionRepository);
    }
}

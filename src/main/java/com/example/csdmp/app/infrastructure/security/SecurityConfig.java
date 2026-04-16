package com.example.csdmp.app.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // On désactive le CSRF car on communique via API (pas de formulaires session-based)
                .csrf(AbstractHttpConfigurer::disable)

                // On définit les règles d'accès
                .authorizeHttpRequests(auth -> auth
                        // On autorise TOUT ce qui est dans le controller Auth (Register/Login)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // On autorise l'accès à la documentation Swagger/OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Tout le reste nécessite une authentification
                        .anyRequest().authenticated()
                )

                // On force le mode STATELESS (pas de JSESSIONID) car on va utiliser des JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
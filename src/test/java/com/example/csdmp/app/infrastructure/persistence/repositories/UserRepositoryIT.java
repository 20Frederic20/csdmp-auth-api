package com.example.csdmp.app.infrastructure.persistence.repositories;

import com.example.csdmp.app.domain.entities.Role;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.security.PasswordInterface;
import com.example.csdmp.app.infrastructure.security.BCryptPasswordHasher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({UserRepositoryImpl.class, RoleRepositoryImpl.class, BCryptPasswordHasher.class})
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
        User user = new User(userId, "Nevis", "KOULOGBO", "lewisnevis44@gmail.com", "012345678910", hashedPassword, true);

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail("lewisnevis44@gmail.com");

        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getPassword()).isEqualTo(hashedPassword);
    }
}

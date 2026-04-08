package com.example.csdmp.app.domain.services;

import com.example.csdmp.app.domain.dtos.PaginatedResult;
import com.example.csdmp.app.domain.entities.User;
import com.example.csdmp.app.domain.exceptions.EntityNotFoundException;
import com.example.csdmp.app.domain.repositories.RoleRepository;
import com.example.csdmp.app.domain.repositories.UserRepository;
import com.example.csdmp.app.domain.security.PasswordInterface;

import java.util.UUID;

public class UserService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    private final PasswordInterface passwordInterface;

    public UserService(
            UserRepository userRepository, RoleRepository roleRepository, PasswordInterface passwordInterface
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordInterface = passwordInterface;
    }

    public User create(String firstName, String lastName, String email, String healthId, String password, boolean isActive){
        String hashedPassword = passwordInterface.hash(password);
        User user = new User(UUID.randomUUID(), firstName, lastName, email, healthId, hashedPassword, isActive);
        userRepository.save(user);
        return user;
    }

    public PaginatedResult<User> getAll(int page, int size){
        return userRepository.findAll(page, size);
    }

    public User update(UUID id, String firstName, String lastName, String email, String healthId, String password, boolean isActive){
        User user = this.findById(id);
        User updatedUser = new User(user.getId(), firstName, lastName, email, healthId, password, isActive);
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public User findById(UUID id){
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur non trouvé avec l'Id : " + id)
        );
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur non trouvé avec l'email : " + email)
        );
    }

    public User findByHealthId(String healthId){
        return userRepository.findByHealthId(healthId).orElseThrow(
                () -> new EntityNotFoundException("Utilisateur non trouvé avec le numéro de crate de santé : " + healthId)
        );
    }

    public void delete(UUID id){
        User user = this.findById(id);
        userRepository.delete(user.getId());
    }
}

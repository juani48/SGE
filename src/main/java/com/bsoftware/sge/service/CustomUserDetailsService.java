package com.bsoftware.sge.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.Role;
import com.bsoftware.sge.repository.ApplicationUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca tu entidad en BD
        ApplicationUser usuario = repository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convierte tu entidad a UserDetails (Spring Security)
        return User.builder()
            .username(usuario.getUsername())
            .password(usuario.getPassword()) // Debe estar hasheada con BCrypt
            .roles(usuario.getRoles().stream().map(Role::getRole).toArray(String[]::new)) // Convierte roles a String[]
            .build();
    }
}
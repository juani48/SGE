package com.bsoftware.sge.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bsoftware.sge.config.CustomUserDetails;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.repository.ApplicationUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser usuario = repository.findByEmailWithRoles(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convierte tu entidad a UserDetails (Spring Security)
        return new CustomUserDetails(usuario);
    }
}
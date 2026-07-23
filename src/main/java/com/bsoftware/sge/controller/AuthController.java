package com.bsoftware.sge.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bsoftware.sge.auxiliar.RoleType;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.Role;
import com.bsoftware.sge.repository.ApplicationUserRepository;
import com.bsoftware.sge.request.RegisterRequest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (applicationUserRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El nombre de usuario ya está registrado");
        }

        ApplicationUser user = new ApplicationUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.addRole(new Role(RoleType.CREATE_PROCEDURE.getText()));

        applicationUserRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@AuthenticationPrincipal Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(authentication.getName());
    }
    
}

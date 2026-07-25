package com.bsoftware.sge.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.Role;
import com.bsoftware.sge.repository.ApplicationUserRepository;
import com.bsoftware.sge.repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Order(2)
public class AdminSeeder implements CommandLineRunner {
    
    private final ApplicationUserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            Role adminRole = roleRepository.findByRole("ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found. Please seed roles first."));
            System.out.println("Seeding " + "Admin" + " ...");
            ApplicationUser admin = new ApplicationUser();
            admin.setName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.addRole(adminRole);
            repository.save(admin);
        }
        System.out.println("Admin seeded.");
    }
}

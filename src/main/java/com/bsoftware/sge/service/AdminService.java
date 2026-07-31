package com.bsoftware.sge.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.auxiliar.RoleType;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.Role;
import com.bsoftware.sge.repository.ApplicationUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AdminService {
    private final ApplicationUserRepository userRepository;

    public Page<ApplicationUser> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Result<ApplicationUser> create(String name, String lastName, String email, String password, List<String> roles) {
        try {
            ApplicationUser user = new ApplicationUser();
            user.setName(name);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setRoles(roles.stream().map(role -> new Role(RoleType.valueOf(role).name())).toList());
            ApplicationUser savedUser = userRepository.save(user);
            return Result.ok(savedUser);
        } catch (Exception e) {
            return Result.fail("Error creating user: " + e.getMessage());
        }
    }

    public Result<ApplicationUser> update(Long id, String name, String lastName, String email, String password, List<String> roles) {
        try {
            return userRepository.findById(id)
                    .map(user -> {
                        user.setName(name);
                        user.setLastName(lastName);
                        user.setEmail(email);
                        if (password != null && !password.isEmpty()) {
                            user.setPassword(password);
                        }
                        user.setRoles(roles.stream().map(role -> new Role(RoleType.valueOf(role).name())).toList());
                        ApplicationUser updatedUser = userRepository.save(user);
                        return Result.ok(updatedUser);
                    })
                    .orElseGet(() -> Result.fail("User not found with id: " + id));
        } catch (Exception e) {
            return Result.fail("Error updating user: " + e.getMessage());
        }
    }

    public Result<ApplicationUser> delete(Long id) {
        try {
            return userRepository.findById(id)
                    .map(user -> {
                        userRepository.delete(user);
                        return Result.ok(user);
                    })
                    .orElseGet(() -> Result.fail("User not found with id: " + id));
        } catch (Exception e) {
            return Result.fail("Error deleting user: " + e.getMessage());
        }
    }
}

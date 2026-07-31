package com.bsoftware.sge.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.repository.ApplicationUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {
    private final ApplicationUserRepository userRepository;

    public Result<ApplicationUser> findById(Long id) {
        Optional<ApplicationUser> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return Result.ok(opt.get());
        } else {
            return Result.fail("User not found with id: " + id);
        }
    }

    public Result<ApplicationUser> update(Long id, String name, String lastName, String email, String password) {
        Optional<ApplicationUser> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            ApplicationUser existingUser = opt.get();
            existingUser.setEmail(email);
            existingUser.setName(name);
            existingUser.setLastName(lastName);
            existingUser.setPassword(password);
            userRepository.save(existingUser);
            return Result.ok(existingUser);
        } else {
            return Result.fail("User not found with id: " + id);
        }
    }
}

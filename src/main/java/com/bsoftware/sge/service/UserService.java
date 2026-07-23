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
}

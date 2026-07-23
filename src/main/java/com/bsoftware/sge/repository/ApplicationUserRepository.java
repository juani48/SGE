package com.bsoftware.sge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bsoftware.sge.model.ApplicationUser;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);
}

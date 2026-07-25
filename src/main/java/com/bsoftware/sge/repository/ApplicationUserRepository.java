package com.bsoftware.sge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bsoftware.sge.model.ApplicationUser;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);

    @Query("SELECT u FROM ApplicationUser u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<ApplicationUser> findByEmailWithRoles(@Param("email") String email);
}

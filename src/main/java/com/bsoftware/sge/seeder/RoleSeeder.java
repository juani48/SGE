package com.bsoftware.sge.seeder;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bsoftware.sge.model.Role;
import com.bsoftware.sge.repository.RoleRepository;

@Component
@Order(1)
public class RoleSeeder extends BaseSeeder<Role, Long> {

    public RoleSeeder(RoleRepository repository) {
        super(
            repository,
            List.of(
                new Role("ADMIN"),
                new Role("CREATE_FILE"),
                new Role("CREATE_PROCEDURE"),
                new Role("EDIT_FILE"),
                new Role("EDIT_PROCEDURE"),
                new Role("DELETE_FILE"),
                new Role("DELETE_PROCEDURE")
            ),
            "Role"
        );
    }
}

package com.bsoftware.sge.seeder;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bsoftware.sge.auxiliar.FileState;
import com.bsoftware.sge.auxiliar.ProcedureState;
import com.bsoftware.sge.auxiliar.RoleType;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.File;
import com.bsoftware.sge.model.Procedure;
import com.bsoftware.sge.model.Role;
import com.bsoftware.sge.repository.ApplicationUserRepository;
import com.bsoftware.sge.repository.FileRepository;
import com.bsoftware.sge.repository.RoleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Order(2)
public class ApplicationSeeder implements CommandLineRunner {

    private final ApplicationUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileRepository fileRepository;

    @Override
    public void run(String... args) {
        System.out.println("Iniciando la creación de usuarios con roles...");
        if (userRepository.count() == 0 && roleRepository.count() > 0)
        {            
            createUserWithRoles(
                "Usuario Creador",
                "Creador",
                "creador@example.com",
                "creador123",
                List.of(RoleType.CREATE_PROCEEDING, RoleType.CREATE_PROCEDURE));

            createUserWithRoles(
                "Usuario Editor",
                "Editor",
                "editor@example.com",
                "editor123",
                List.of(RoleType.EDIT_PROCEEDING, RoleType.EDIT_PROCEDURE));

            createUserWithRoles(
                "Usuario Eliminador",
                "Eliminador",
                "eliminador@example.com",
                "eliminador123",
                List.of(RoleType.DELETE_PROCEEDING, RoleType.DELETE_PROCEDURE));

            createUserWithRoles(
                "Admin",
                "Admin",
                "admin@example.com",
                "admin123",
                List.of(RoleType.ADMIN));
        }
        System.out.println("Usuarios y roles creados correctamente.");
        System.out.println("Iniciando la creación de archivos con procedimientos...");
        if (fileRepository.count() == 0) {
            createFilesWithProcedures(userRepository.findByEmail("admin@example.com").get());
        }
        System.out.println("Expedientes y trámites creados correctamente.");
    }

    private void createUserWithRoles(
        String name, String lastName, String email, String rawPassword, List<RoleType> roleTypes
    ) {
        if (userRepository.findByEmail(email).isPresent()) {
            return;
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        ApplicationUser user = new ApplicationUser(name, lastName, email, encodedPassword);

        roleTypes.forEach(roleType -> {
            Role role = roleRepository.findByRole(roleType.getText()).get();
            user.addRole(role);
        });

        userRepository.save(user);
        System.out.println("Usuario creado: " + email + " con roles: " + roleTypes);
    }

    private void createFilesWithProcedures(ApplicationUser user) {
        for (int i = 0; i < 23; i++) {
            File file = new File();
            file.setCover("Expediente N° " + i);
            file.setState(FileState.NEWLY_STARTED);
            file.setCreation(LocalDate.now());
            file.setModification(LocalDate.now());
            file.setModificationUser(user);

            for (int j = 1; j <= 3; j++) {
                Procedure procedure = new Procedure();
                procedure.setContent("Contenido del procedimiento " + j + " del expediente " + i);
                procedure.setState(ProcedureState.DOCUMENT_SUBMITTED);
                procedure.setCreation(LocalDate.now());
                procedure.setModification(LocalDate.now());
                procedure.setModificationUser(user);
                procedure.setFile(file); 

                file.getProcedures().add(procedure);
            }

            fileRepository.save(file);
        }
    }
}

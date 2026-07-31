package com.bsoftware.sge.dto.user;

import java.util.List;

import com.bsoftware.sge.model.ApplicationUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProfileUserDto {
    private Long id;
    private String email;
    private String name;
    private String lastName;
    private List<String> roles;

    public ProfileUserDto(ApplicationUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.roles = user.getRoles().stream() .map(role -> role.getRole()) .toList();
    }
}

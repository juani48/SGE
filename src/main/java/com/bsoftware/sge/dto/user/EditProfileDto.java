package com.bsoftware.sge.dto.user;

import com.bsoftware.sge.model.ApplicationUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class EditProfileDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;

    public EditProfileDto(ApplicationUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}

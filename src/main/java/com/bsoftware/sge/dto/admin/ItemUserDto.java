package com.bsoftware.sge.dto.admin;

import java.util.List;

import com.bsoftware.sge.model.ApplicationUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ItemUserDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private List<String> roles;

    public ItemUserDto(ApplicationUser user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream() .map(role -> role.getRole()) .toList();
    }
}

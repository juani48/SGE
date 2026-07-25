package com.bsoftware.sge.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterUserDto {
    
    @NotBlank(message = "Email is required")
    @Email 
    private String email;
    
    @NotBlank(message = "Password is required") 
    @Size(min = 8, message = "Password must be at least 8 characters long") 
    private String password;
}

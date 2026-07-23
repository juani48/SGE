package com.bsoftware.sge.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RegisterRequest {
    
    @NotBlank(message = "Username is required") 
    private String username;
    
    @NotBlank(message = "Password is required") 
    @Size(min = 8, message = "Password must be at least 8 characters long") 
    private String password;
}

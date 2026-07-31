package com.bsoftware.sge.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsoftware.sge.config.CustomUserDetails;
import com.bsoftware.sge.dto.user.EditProfileDto;
import com.bsoftware.sge.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("editProfile") EditProfileDto dto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.update(userDetails.getId(), dto.getName(), dto.getLastName(), dto.getEmail(), dto.getPassword());
        return "redirect:/profile";
    }
}

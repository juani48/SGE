package com.bsoftware.sge.render;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.config.CustomUserDetails;
import com.bsoftware.sge.dto.user.EditProfileDto;
import com.bsoftware.sge.dto.user.ProfileUserDto;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.service.FileService;
import com.bsoftware.sge.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class UserRenderController {
    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("fileCount", fileService.count());
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Result<ApplicationUser> result = userService.findById(userDetails.getId());
        if (result.isOk()) {
            model.addAttribute("user", new ProfileUserDto(result.getData()));
            return "user/profile";
        } else {
            model.addAttribute("message", "No se pudo acceder al perfil");
            return "error";
        }
    }

    @GetMapping("/profile/update")
    public String showEditProfile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Result<ApplicationUser> result = userService.findById(userDetails.getId());
        if (result.isOk()) {
            model.addAttribute("editProfile", new EditProfileDto(result.getData()));
            return "user/profile-edit";
        } else {
            return "error";
        }
    }

}

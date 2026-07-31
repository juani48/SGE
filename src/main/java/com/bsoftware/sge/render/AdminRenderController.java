package com.bsoftware.sge.render;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.dto.admin.FormUserDto;
import com.bsoftware.sge.dto.admin.PageUserDto;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.service.AdminService;
import com.bsoftware.sge.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminRenderController {
    private final UserService userService;
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String listUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<ApplicationUser> pageDto = adminService.getAll(PageRequest.of(page, size));
        model.addAttribute("page", new PageUserDto(pageDto));
        return "admin/user-list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/create")
    public String showCreateUser(Model model) {
        model.addAttribute("user", new FormUserDto());
        return "admin/user-form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users/update/{id}")
    public String showUpdateUser(@PathVariable Long id, Model model) {
        Result<ApplicationUser> result = userService.findById(id);
        if (result.isOk()) {
            model.addAttribute("user", new FormUserDto(result.getData()));
            return "admin/user-form";
        } else {
            model.addAttribute("message", "Usuario no encontrado");
            return "error";
        }
    }
}

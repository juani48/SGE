package com.bsoftware.sge.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.bsoftware.sge.dto.admin.FormUserDto;
import com.bsoftware.sge.service.AdminService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") FormUserDto dto) {
        adminService.create(dto.getName(), dto.getLastName(), dto.getEmail(), dto.getPassword(), dto.getRoles());
        return "redirect:/admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") FormUserDto dto) {
        adminService.update(id, dto.getName(), dto.getLastName(), dto.getEmail(), dto.getPassword(), dto.getRoles());
        return "redirect:/admin/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        adminService.delete(id);
        return "redirect:/admin/users";
    }
}

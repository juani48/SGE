package com.bsoftware.sge.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.config.CustomUserDetails;
import com.bsoftware.sge.dto.file.FormFileDto;
import com.bsoftware.sge.model.File;
import com.bsoftware.sge.service.FileService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CREATE_FILE') or hasRole('ADMIN')")
    public String create(@Valid @ModelAttribute FormFileDto request, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        Result<File> result = fileService.create(request.getCover(), userDetails.getId());
        if (result.isOk()) {
            redirectAttributes.addFlashAttribute("successMessage", "File created successfully");
            return "redirect:/file/detail/" + result.getData().getId(); 
        }  
        else {
            redirectAttributes.addFlashAttribute("errorMessage", result.getError());
            return "redirect:/files/form"; 
        }
    }
    
    @PostMapping("/update")
    @PreAuthorize("hasRole('UPDATE_FILE') or hasRole('ADMIN')")
    public String update(@Valid @ModelAttribute FormFileDto request, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        Result<File> result = fileService.update(request.getId(), request.getCover(), request.getState(), userDetails.getId());
        if (result.isOk()) {
            System.out.println("File updated successfully: " + result.getData());
            redirectAttributes.addFlashAttribute("successMessage", "File updated successfully");
            return "redirect:/file/detail/" + result.getData().getId(); 
        } else {
            System.out.println("Error updating file: " + result.getError());
            redirectAttributes.addFlashAttribute("errorMessage", result.getError());
            return "redirect:/file/form";
        }
    }
    
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('DELETE_FILE') or hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result<Void> result = fileService.delete(id);
        if (result.isOk()) {
            redirectAttributes.addFlashAttribute("successMessage", "File deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", result.getError());
        }
        return "redirect:/files"; // Redirect back to the list of files
    }
    
}

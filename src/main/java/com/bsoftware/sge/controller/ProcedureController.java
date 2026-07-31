package com.bsoftware.sge.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.config.CustomUserDetails;
import com.bsoftware.sge.dto.procedure.FormProcedureDto;
import com.bsoftware.sge.model.Procedure;
import com.bsoftware.sge.service.ProcedureService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/api/procedure")
public class ProcedureController {
    private final ProcedureService procedureService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('CREATE_PROCEDURE') or hasRole('ADMIN')")
    public String create(@Valid @ModelAttribute FormProcedureDto request, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        Result<Procedure> result = procedureService.create(request.getContent(), request.getFileId(), userDetails.getId());
        if (result.isOk()) {
            redirectAttributes.addFlashAttribute("successMessage", "Procedure created successfully");
            redirectAttributes.addFlashAttribute("procedure", result.getData());
            return "redirect:/procedure/detail/" + result.getData().getId(); // Redirect to the detail page of the created procedure
        }  
        else {
            redirectAttributes.addFlashAttribute("errorMessage", result.getError());
            return "redirect:/procedure/create";
        }
    }
    
    @PostMapping("/update")
    @PreAuthorize("hasRole('UPDATE_PROCEDURE') or hasRole('ADMIN')")
    public String update(@Valid @ModelAttribute FormProcedureDto request, @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        Result<Procedure> result = procedureService.update(request.getContent(), request.getFileId(), request.getState(), userDetails.getId());
        if (result.isOk()) {
            redirectAttributes.addFlashAttribute("successMessage", "Procedure updated successfully");
            redirectAttributes.addFlashAttribute("procedure", result.getData());
            return "redirect:/procedure/detail/" + result.getData().getId(); // Redirect to the detail page of the updated procedure
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", result.getError());
            return "redirect:/procedure/update"; // Redirect back to the edit procedure page
        }
    }
    
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('DELETE_PROCEDURE') or hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Result<Void> result = procedureService.delete(id);
        if (result.isOk()) {
            redirectAttributes.addFlashAttribute("successMessage", "Procedure deleted successfully");
            return "redirect:/files"; // Redirect to the list of files after successful deletion
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", result.getError());
            return "redirect:/procedure/" + id; // Redirect back to the edit file page
        }
    }
}
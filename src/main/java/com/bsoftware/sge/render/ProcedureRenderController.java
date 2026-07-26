package com.bsoftware.sge.render;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.dto.procedure.DetailProcedureDto;
import com.bsoftware.sge.dto.procedure.FormProcedureDto;
import com.bsoftware.sge.model.Procedure;
import com.bsoftware.sge.service.ProcedureService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ProcedureRenderController {
    private final ProcedureService procedureService;

    @GetMapping("/procedure/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Result<Procedure> result = procedureService.getById(id);
        if (result.isOk()) {
            model.addAttribute("procedure", new DetailProcedureDto(result.getData()));
            return "procedure/detail"; 
        } else {
            model.addAttribute("message", "El trámite al que intenta acceder no existe.");
            return "error"; 
        }
    }
    
    @GetMapping("/procedure/create/{fileId}")
    public String create(@PathVariable Long fileId, Model model) {
        model.addAttribute("procedure", new FormProcedureDto(fileId));
        return "procedure/form"; 
    }
    
    @GetMapping("/procedure/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Result<Procedure> procedure = procedureService.getById(id);
        if (procedure.isOk()) {
            model.addAttribute("procedure", new FormProcedureDto(procedure.getData()));
            return "procedure/form"; // Render the create view
        } else {
            model.addAttribute("message", "El trámite al que intenta acceder no existe.");
            return "error"; 
        }
    }
}

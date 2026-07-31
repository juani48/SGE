package com.bsoftware.sge.render;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.dto.procedure.DetailProcedureDto;
import com.bsoftware.sge.dto.procedure.FormProcedureDto;
import com.bsoftware.sge.model.Procedure;
import com.bsoftware.sge.service.ProcedureService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
@RequestMapping("/procedure")
public class ProcedureRenderController {
    private final ProcedureService procedureService;

    @ModelAttribute("procedureStateClassMap")
    public Map<String, String> getProcedureStateStyle() {
        return Map.of(
                "Documento presentado", "badge-document-submitted",
                "Derivado a revisión", "badge-referred-review",
                "Despacho", "badge-office",
                "Resolución", "badge-resolution",
                "Notificación", "badge-notification",
                "Derivado a archivo", "badge-forwarded-archive");
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Result<Procedure> result = procedureService.getById(id);
        if (result.isOk()) {
            model.addAttribute("procedure", new DetailProcedureDto(result.getData()));
            model.addAttribute("procedureStateClassMap", getProcedureStateStyle());
            return "procedure/detail"; 
        } else {
            model.addAttribute("message", "El trámite al que intenta acceder no existe.");
            return "error"; 
        }
    }
    
    @GetMapping("/create/{fileId}")
    public String create(@PathVariable Long fileId, Model model) {
        model.addAttribute("procedureStateClassMap", getProcedureStateStyle());
        model.addAttribute("procedure", new FormProcedureDto(fileId));
        return "procedure/form"; 
    }
    
    @GetMapping("/update/{id}")
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

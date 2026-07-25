package com.bsoftware.sge.render;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.dto.file.FormFileDto;
import com.bsoftware.sge.dto.procedure.DetailProcedureDto;
import com.bsoftware.sge.dto.procedure.UpdateProcedureDto;
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
            return "procedure/detail"; // Render the detail view
        } else {
            return "error/404"; // Render a 404 error page if the procedure is not found
        }
    }
    
    @GetMapping("/procedure/create")
    public String create(Model model) {
        model.addAttribute("procedure", new FormFileDto());
        return "file/create"; // Render the create view
    }
    
    @GetMapping("/procedure/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Result<Procedure> procedure = procedureService.getById(id);
        if (procedure.isOk()) {
            model.addAttribute("procedure", new UpdateProcedureDto(procedure.getData()));
            return "procedure/create"; // Render the create view
        } else {
            return "error/404"; // Render a 404 error page if the file is not found
        }
    }
}

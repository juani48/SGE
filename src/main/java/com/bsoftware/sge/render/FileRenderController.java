package com.bsoftware.sge.render;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.dto.file.DetailFileDto;
import com.bsoftware.sge.dto.file.FormFileDto;
import com.bsoftware.sge.dto.file.PageFileDto;
import com.bsoftware.sge.dto.procedure.ItemProcedureDto;
import com.bsoftware.sge.model.File;
import com.bsoftware.sge.model.Procedure;
import com.bsoftware.sge.service.FileService;
import com.bsoftware.sge.service.ProcedureService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class FileRenderController {
    private final FileService fileService;
    private final ProcedureService procedureService;

    @GetMapping("/file/{id}")
    public String detail(@PathVariable Long id, Model model, @PageableDefault(size = 5, sort = "creation", direction = Direction.DESC) Pageable pageable) {
        Result<File> result = fileService.getById(id);
        if (!result.isOk()) {
            model.addAttribute("message", "El expediente al que intenta acceder no existe.");
            return "error";
            }
        File file = result.getData();

        Page<Procedure> procedurePage = procedureService.getAllByFileId(id, pageable);
        Page<ItemProcedureDto> procedureDtoPage = procedurePage.map(ItemProcedureDto::new);

        // Pasar al modelo
        model.addAttribute("file", new DetailFileDto(file));
        model.addAttribute("procedurePage", procedureDtoPage);

        // Mapa de clases CSS para estados (puedes mantenerlo)
        model.addAttribute("stateClassMap", Map.of(
            "Recién iniciado", "bg-info",
            "Por resolver", "bg-warning",
            "Con resolución", "bg-success",
            "En notificación", "bg-primary",
            "Completado", "bg-secondary"
        ));

        return "file/detail";
    }
    
    @GetMapping("/files")
    public String list(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<File> filePage = fileService.getAll(pageable);
        model.addAttribute("page", new PageFileDto(filePage));
        model.addAttribute("stateClassMap", Map.of(
                "Recién iniciado", "bg-info",
                "Por resolver", "bg-warning",
                "Con resolución", "bg-success",
                "En notificación", "bg-primary",
                "Completado", "bg-secondary"
            ));
        return "file/list"; // Render the list view
    }
    
    @GetMapping("/file/create")
    public String create(Model model) {
        model.addAttribute("file", new FormFileDto());
        return "file/form"; // Render the create view
    }
    
    @GetMapping("/file/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Result<File> file = fileService.getById(id);
        if (file.isOk()) {
            model.addAttribute("file", new FormFileDto(file.getData()));
            return "file/form"; // Render the update view
        } else {
            model.addAttribute("message", "El expediente al que intenta acceder no existe.");
            return "error";
        }
    }
}

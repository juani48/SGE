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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
@RequestMapping("/file")
public class FileRenderController {
    private final FileService fileService;
    private final ProcedureService procedureService;

    @ModelAttribute("fileStateClassMap")
    public Map<String, String> getFileStateStyle() {
        return Map.of(
                "Recién iniciado", "badge-newly-started",
                "Por resolver", "badge-in-progress",
                "Con resolución", "badge-resolved",
                "En notificación", "badge-in-notification",
                "Completado", "badge-completed");
    }

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
    public String detail(@PathVariable Long id, Model model, @PageableDefault(size = 5, sort = "creation", direction = Direction.DESC) Pageable pageable) {
        Result<File> result = fileService.getById(id);
        if (!result.isOk()) {
            model.addAttribute("message", "El expediente al que intenta acceder no existe.");
            return "error";
        }
        File file = result.getData();

        Page<Procedure> procedurePage = procedureService.getAllByFileId(id, pageable);
        Page<ItemProcedureDto> procedureDtoPage = procedurePage.map(ItemProcedureDto::new);

        model.addAttribute("file", new DetailFileDto(file));
        model.addAttribute("procedures", procedureDtoPage);
        model.addAttribute("fileStateClassMap", this.getFileStateStyle());
        model.addAttribute("procedureStateClassMap", this.getProcedureStateStyle());

        return "file/detail";
    }

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<File> filePage = fileService.getAll(pageable);
        model.addAttribute("page", new PageFileDto(filePage));
        model.addAttribute("fileStateClassMap", this.getFileStateStyle());
        return "file/list"; // Render the list view
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("file", new FormFileDto());
        model.addAttribute("fileStateClassMap", this.getFileStateStyle());
        return "file/form"; // Render the create view
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Result<File> file = fileService.getById(id);
        if (file.isOk()) {
            model.addAttribute("file", new FormFileDto(file.getData()));
            model.addAttribute("stateClassMap", Map.of(
                    "Recién iniciado", "bg-info",
                    "Por resolver", "bg-warning",
                    "Con resolución", "bg-success",
                    "En notificación", "bg-primary",
                    "Completado", "bg-secondary"));
            return "file/form"; // Render the update view
        } else {
            model.addAttribute("message", "El expediente al que intenta acceder no existe.");
            return "error";
        }
    }
}

package com.bsoftware.sge.render;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.dto.file.DetailFileDto;
import com.bsoftware.sge.dto.file.FormFileDto;
import com.bsoftware.sge.dto.file.ListFileDto;
import com.bsoftware.sge.model.File;
import com.bsoftware.sge.service.FileService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AllArgsConstructor
@Controller
public class FileRenderController {
    private final FileService fileService;

    @GetMapping("/file/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Result<File> file = fileService.getById(id);
        if (file.isOk()) {
            model.addAttribute("file", new DetailFileDto(file.getData()));
            return "file/detail"; // Render the detail view
        } else {
            return "error/404"; // Render a 404 error page if the file is not found
        }
    }
    
    @GetMapping("/files")
    public String list(Model model) {
        List<File> files = fileService.getAll();
        model.addAttribute("files", new ListFileDto(files));
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
            return "error/404"; // Render a 404 error page if the file is not found
        }
    }
}

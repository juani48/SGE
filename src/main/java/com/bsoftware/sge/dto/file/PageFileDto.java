package com.bsoftware.sge.dto.file;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.bsoftware.sge.model.File;

import lombok.Data;
import lombok.Getter;



@Getter
@Data
public class PageFileDto {
    private List<ItemFileDto> content;     // los elementos de la página
    private int totalPages;
    private long totalElements;
    private int number;                    // número de página actual (0-indexado)
    private int size;                      // tamaño de la página
    private boolean first;
    private boolean last;
    private boolean empty;

    public PageFileDto(Page<File> page) {
        this.content = page.getContent().stream()
            .map(ItemFileDto::new)
            .collect(Collectors.toList());
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.number = page.getNumber();
        this.size = page.getSize();
        this.first = page.isFirst();
        this.last = page.isLast();
        this.empty = page.isEmpty();
    }
}
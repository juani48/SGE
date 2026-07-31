package com.bsoftware.sge.dto.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.bsoftware.sge.model.ApplicationUser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PageUserDto {
    private List<ItemUserDto> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private boolean first;
    private boolean last;
    private boolean empty;

    public PageUserDto(Page<ApplicationUser> page) {
        this.content = page.getContent().stream()
            .map(ItemUserDto::new)
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

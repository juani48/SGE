package com.bsoftware.sge.dto.file;

import java.util.List;
import java.util.stream.Collectors;

import com.bsoftware.sge.model.File;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
class ItemFileDto {
    private Long id;
    private String cover;
    private String state;

    public ItemFileDto(File file) {
        this.id = file.getId();
        this.cover = file.getCover();
        this.state = file.getState().name();
    }
}

@Getter
@Data
public class ListFileDto {
    private List<ItemFileDto> files;

    public ListFileDto(List<File> files) {
        this.files = files.stream().map(ItemFileDto::new).collect(Collectors.toList());
    }
}
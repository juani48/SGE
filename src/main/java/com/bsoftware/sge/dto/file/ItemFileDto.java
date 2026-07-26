package com.bsoftware.sge.dto.file;

import com.bsoftware.sge.auxiliar.FileState;
import com.bsoftware.sge.model.File;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ItemFileDto {
    private Long id;
    private String cover;
    private String state;

    public ItemFileDto(File file) {
        this.id = file.getId();
        this.cover = file.getCover();
        this.state = FileState.valueOf(file.getState().name()).getContent();
    }
}

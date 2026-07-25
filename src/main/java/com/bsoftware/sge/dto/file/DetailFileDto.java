package com.bsoftware.sge.dto.file;

import com.bsoftware.sge.model.File;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class DetailFileDto {
    private Long id;
    private String cover;
    private String state;
    private String creation;
    private String modification;
    private String modificationUserEmail;

    public DetailFileDto(File file) {
        this.id = file.getId();
        this.cover = file.getCover();
        this.state = file.getState().name();
        this.creation = file.getCreation().toString();
        this.modification = file.getModification().toString();
        this.modificationUserEmail = file.getModificationUser().getEmail();
    }
}

package com.bsoftware.sge.dto.file;

import com.bsoftware.sge.model.File;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class FormFileDto {
    private Long id;
    private String cover;
    private String state;

    public FormFileDto(File file) {
        this.id = file.getId();
        this.cover = file.getCover();
        this.state = file.getState().name();
    }
}

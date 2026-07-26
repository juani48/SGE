package com.bsoftware.sge.dto.procedure;

import com.bsoftware.sge.model.Procedure;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class FormProcedureDto {
    private Long id;
    private Long fileId;
    private String content;
    private String state;

    public FormProcedureDto(Procedure procedure) {
        this.id = procedure.getId();
        this.fileId = procedure.getFile().getId();
        this.content = procedure.getContent();
        this.state = procedure.getState().name();
    }

    public FormProcedureDto(Long fileId) {
        this.fileId = fileId;
    }
}

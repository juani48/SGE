package com.bsoftware.sge.dto.procedure;

import com.bsoftware.sge.model.Procedure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class DetailProcedureDto {
    private Long id;
    private String state;
    private String content;
    private String creation;
    private String modification;
    private String modificationUser;
    private Long fileId;

    public DetailProcedureDto(Procedure procedure) {
        this.id = procedure.getId();
        this.state = procedure.getState().name();
        this.content = procedure.getContent();
        this.creation = procedure.getCreation().toString();
        this.modification = procedure.getModification().toString();
        this.modificationUser = procedure.getModificationUser().getEmail();
        this.fileId = procedure.getFile().getId();
    }
}

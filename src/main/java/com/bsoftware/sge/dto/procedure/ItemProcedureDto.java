package com.bsoftware.sge.dto.procedure;

import com.bsoftware.sge.model.Procedure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ItemProcedureDto {
    private Long id;
    private String content;
    private String state;
    private String creation;

    public ItemProcedureDto(Procedure procedure) {
        this.id = procedure.getId();
        this.content = procedure.getContent();
        this.state = procedure.getState().getContent();
        this.creation = procedure.getCreation().toString();
    }
}
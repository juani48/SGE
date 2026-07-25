package com.bsoftware.sge.dto.procedure;

import com.bsoftware.sge.model.Procedure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateProcedureDto {
    private Long id;
    private String content;
    private String state;

    public UpdateProcedureDto(Procedure procedure) {
        this.id = procedure.getId();
        this.content = procedure.getContent();
        this.state = procedure.getState().name();
    }
}

package com.bsoftware.sge.dto.procedure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateProcedureDto {
    private Long fileId;
    private String content;
}

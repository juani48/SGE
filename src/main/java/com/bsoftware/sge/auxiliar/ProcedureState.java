package com.bsoftware.sge.auxiliar;

public enum ProcedureState {
    DOCUMENT_SUBMITTED("Documento presentado"),
    REFERRED_FOR_REVIEW("Derivado a revisión"),
    OFFICE("Despacho"),
    RESOLUTION("Resolución"),
    NOTIFICATION("Notificación"),
    FORWARDED_TO_ARCHIVE("Derivado a archivo");

    private final String content;

    ProcedureState(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

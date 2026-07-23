package com.bsoftware.sge.auxiliar;

public enum FileState {
    NEWLY_STARTED("Recién iniciado"),
    TO_BE_RESOLVED("Por resolver"),
    RESOLVED("Con resolución"),
    IN_NOTIFICATION("En notificación"),
    COMPLETED("Completado");

    private final String content;

    FileState(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

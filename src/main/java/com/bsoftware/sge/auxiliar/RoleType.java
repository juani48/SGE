package com.bsoftware.sge.auxiliar;

public enum RoleType {
    ADMIN("ADMIN"),
    CREATE_PROCEEDING("CREATE_PROCEEDING"),
    CREATE_PROCEDURE("CREATE_PROCEDURE"),
    EDIT_PROCEEDING("EDIT_PROCEEDING"),
    EDIT_PROCEDURE("EDIT_PROCEDURE"),
    DELETE_PROCEEDING("DELETE_PROCEEDING"),
    DELETE_PROCEDURE("DELETE_PROCEDURE");

    private final String text;

    RoleType(String roleName) {
        this.text = roleName;
    }

    public String getText() {
        return text;
    }
}

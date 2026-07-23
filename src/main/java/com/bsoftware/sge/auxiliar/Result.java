package com.bsoftware.sge.auxiliar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Result<T> {

    private final T data;
    private final String error;

    public static <T> Result<T> ok(T data) {
        return new Result<>(data, null);
    }

    public static <T> Result<T> fail(String error) {
        return new Result<>(null, error);
    }

    public boolean isOk() {
        return data != null;
    }
}
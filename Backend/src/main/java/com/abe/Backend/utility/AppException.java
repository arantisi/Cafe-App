package com.abe.Backend.utility;

import lombok.*;

public class AppException extends RuntimeException {
    @Getter
    private final int code;

    public AppException(String message) {
        this(message, 400);
    }

    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }
}
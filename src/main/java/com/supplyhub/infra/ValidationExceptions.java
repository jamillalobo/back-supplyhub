package com.supplyhub.infra;

public class ValidationExceptions extends RuntimeException {
    public ValidationExceptions(String message) {
        super(message);
    }
}

package com.supplyhub.infra.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionLogger {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex, HttpServletRequest request) {
        System.err.println("⚠️ Exceção em: " + request.getRequestURI());
        ex.printStackTrace(); // Mostra o erro no terminal
    }
}

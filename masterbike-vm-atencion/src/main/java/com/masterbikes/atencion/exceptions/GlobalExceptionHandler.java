package com.masterbikes.atencion.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> detalles = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            detalles.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(error("Error de validacion", 400, request.getRequestURI(), detalles));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarReglas(ResponseStatusException ex, HttpServletRequest request) {
        int status = ex.getStatusCode().value();
        return ResponseEntity.status(status).body(error(ex.getReason(), status, request.getRequestURI(), null));
    }

    private Map<String, Object> error(String mensaje, int status, String path, Object detalles) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        body.put("message", mensaje);
        body.put("path", path);
        body.put("details", detalles);
        return body;
    }
}

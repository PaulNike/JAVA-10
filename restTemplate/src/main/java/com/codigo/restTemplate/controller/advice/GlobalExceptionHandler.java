package com.codigo.restTemplate.controller.advice;

import com.codigo.restTemplate.exception.ConsultaReniecException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConsultaReniecException.class)
    public ResponseEntity<Map<String, String>> handleConsultaReniecException(
            ConsultaReniecException ex){
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("error", "RENIEC_SERVICE_ERROR");
        errorBody.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorBody);
    }
}

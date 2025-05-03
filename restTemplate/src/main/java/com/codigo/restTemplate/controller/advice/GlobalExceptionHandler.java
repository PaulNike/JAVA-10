package com.codigo.restTemplate.controller.advice;

import com.codigo.restTemplate.aggregates.response.ApiErrorResponse;
import com.codigo.restTemplate.exception.ConsultaReniecException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorResponse> handleConsultaReniecException(
            Throwable ex){
        ApiErrorResponse apiErrorResponse =
                new ApiErrorResponse(ex.getClass().getSimpleName(), ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(ex instanceof ConsultaReniecException){
            status = HttpStatus.BAD_GATEWAY;
        }
        //N excepciones que manejes por medio de Else if

        return ResponseEntity.status(status).body(apiErrorResponse);
    }
   // @ExceptionHandler(ConsultaReniecException.class)
   // public ResponseEntity<Map<String, String>> handleConsultaReniecException(
   //         ConsultaReniecException ex){
   //     Map<String, String> errorBody = new HashMap<>();
   //     errorBody.put("error", "RENIEC_SERVICE_ERROR");
   //     errorBody.put("message", ex.getMessage());
//
   //     return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorBody);
   // }
}

package com.sasa.user_service.adapter.input.rest;

import com.sasa.user_service.adapter.input.dto.response.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<String>> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<String>> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.append(fieldError.getField())
                    .append(": ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        return ResponseEntity.badRequest()
                .body(new ApiResponseDto<>(400, "Validation failed: " + errors.toString(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<String>> handleGeneralException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDto<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal server error: " + ex.getMessage(),
                        null));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponseDto<String>> handleSecurityException(SecurityException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponseDto<>(HttpStatus.UNAUTHORIZED.value(),
                        "Unauthorized: " + ex.getMessage(),
                        null));
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponseDto<String>> handleWebClientException(WebClientResponseException ex) {
        return ResponseEntity.status(ex.getRawStatusCode())
                .body(new ApiResponseDto<>(
                        ex.getRawStatusCode(),
                        "Error from upstream service: " + ex.getResponseBodyAsString(),
                        null
                ));
    }
}

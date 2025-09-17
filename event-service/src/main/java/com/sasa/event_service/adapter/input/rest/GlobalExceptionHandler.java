package com.sasa.event_service.adapter.input.rest;

import com.sasa.event_service.domain.dto.common.ApiResponseDto;
import com.sasa.event_service.domain.exception.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<String>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(new ApiResponseDto<>(400, errorMessage, null));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseDto<String>> handleNullPointerException(NullPointerException ex) {
        String message = "Invalid request: missing or null value detected - " + ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(400, message, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDto<String>> handleJsonParseError(HttpMessageNotReadableException ex) {
        String message = "Malformed JSON request";

        if (ex.getCause() instanceof DateTimeParseException dtEx) {
            message = "Invalid date format: " + dtEx.getParsedString() +
                    ". Expected format: yyyy-MM-dd'T'HH:mm:ss";
        }

        return ResponseEntity.badRequest()
                .body(new ApiResponseDto<>(400, message, null));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponseDto<>(400, ex.getMessage(), null));
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiResponseDto<String>> handleEventNotFound(EventNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDto<>(404, ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<String>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDto<>(500, "Internal server error: " + ex.getMessage(), null));
    }
}

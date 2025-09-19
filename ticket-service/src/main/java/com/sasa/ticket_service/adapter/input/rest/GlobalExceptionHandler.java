package com.sasa.ticket_service.adapter.input.rest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sasa.ticket_service.adapter.input.dto.response.ApiResponseDto;
import com.sasa.ticket_service.domain.exception.TicketNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.PersistenceException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseDto<String>> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponseDto<>(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponseDto<>(HttpStatus.FORBIDDEN.value(), ex.getMessage(), null));
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleTicketNotFound(TicketNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDto<>(404, ex.getMessage(), null));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponseDto<String>> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponseDto<>(400, ex.getMessage(), null));
    }


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

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponseDto<String>> handleWebClientException(WebClientResponseException ex) {
        String message = ex.getResponseBodyAsString();
        int status = ex.getStatusCode().value();
        return ResponseEntity
                .status(status)
                .body(new ApiResponseDto<>(status, message, null));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponseDto<String>> handleNullPointerException(NullPointerException ex) {
        String message = "Invalid request: missing or null value detected - " + ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(400, message, null));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleJsonParse(HttpMessageNotReadableException ex) {
        String message = "Invalid JSON input";
        if (ex.getCause() instanceof InvalidFormatException cause) {
            String fieldName = cause.getPath().get(0).getFieldName();
            message = String.format("Invalid value for field '%s': %s", fieldName, cause.getValue());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(400, message, null));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDataIntegrity(DataIntegrityViolationException ex) {
        String message = "Invalid data: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(400, message, null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(400, ex.getMessage(), null));
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ApiResponseDto<Void>> handlePersistence(PersistenceException ex) {
        String message = "Invalid input data: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDto<>(400, message, null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(400)
                .body(new ApiResponseDto<>(400, ex.getMessage(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneralException(Exception ex) {
        String message = "An unexpected error occurred: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDto<>(500, message, null));
    }
}

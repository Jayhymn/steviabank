package com.wakeupdev.steviabank.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Data integrity violation at {}: {}", request.getRequestURI(), ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, "Resource already exists", request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Malformed request body at {}: {}", request.getRequestURI(), ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed request body", request);
    }

    @ExceptionHandler({DuplicateEmailException.class, DuplicatePhoneNumberException.class})
    public ResponseEntity<ErrorResponse> handleDuplicateException(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler({InvalidOtpException.class, InvalidPhoneNumberException.class})
    public ResponseEntity<ErrorResponse> handleInvalidInputException(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception at {}", request.getRequestURI(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request) {
        return buildResponse(status, message, request, Optional.empty());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message, HttpServletRequest request, Optional<List<FieldViolation>> fieldErrors) {
        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                Instant.now(),
                request.getRequestURI(),
                fieldErrors.orElse(null)
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}

package com.wakeupdev.steviabank.common.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIntegrityViolation_returnsConflictWithSafeMessage() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/customers/register");
        DataIntegrityViolationException ex = new DataIntegrityViolationException(
                "duplicate key value violates unique constraint \"uk_account_number\"");

        ResponseEntity<ErrorResponse> response = handler.handleIntegrityViolation(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(409);
        assertThat(response.getBody().error()).isEqualTo("Conflict");
        assertThat(response.getBody().message()).isEqualTo("Resource already exists");
        assertThat(response.getBody().path()).isEqualTo("/api/v1/customers/register");
    }

    @Test
    void handleBusinessException_returnsStatusCodeAndMessageWithoutFieldErrors() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/otp/verify");
        BusinessException ex = new BusinessException(ErrorCode.OTP_INVALID, "Custom message");

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().status()).isEqualTo(400);
        assertThat(response.getBody().message()).isEqualTo("Custom message");
        assertThat(response.getBody().fieldErrors()).isNull();
    }

    @Test
    void handleBusinessException_returnsFieldErrorsWhenPresent() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/v1/otp/verify");
        List<FieldViolation> violations = List.of(new FieldViolation("otp", "Invalid OTP supplied"));
        OtpException ex = new OtpException(ErrorCode.OTP_ALREADY_USED, violations);

        ResponseEntity<ErrorResponse> response = handler.handleBusinessException(ex, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().message()).isEqualTo(ErrorCode.OTP_ALREADY_USED.getDefaultMessage());
        assertThat(response.getBody().fieldErrors()).containsExactlyElementsOf(violations);
    }
}

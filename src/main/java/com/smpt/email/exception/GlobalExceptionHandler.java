package com.smpt.email.exception;

import com.smpt.email.dtos.ApiResponse;
import com.smpt.email.dtos.EmailRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ApiResponse<EmailRequest>> handleEmailException(
            EmailSendingException ex) {

        ApiResponse<EmailRequest> response = ApiResponse.<EmailRequest>builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}

package com.smpt.email.controller;


import com.smpt.email.dtos.ApiResponse;
import com.smpt.email.dtos.EmailRequest;
import com.smpt.email.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<EmailRequest>> sendEmail(@Valid @RequestBody EmailRequest emailRequest) {
        ApiResponse<EmailRequest> response = emailService.sendEmail(emailRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

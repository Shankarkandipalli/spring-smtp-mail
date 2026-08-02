package com.smpt.email.service;

import com.smpt.email.dtos.ApiResponse;
import com.smpt.email.dtos.EmailRequest;

public interface EmailService {
    ApiResponse<EmailRequest> sendEmail(EmailRequest emailRequest);
}

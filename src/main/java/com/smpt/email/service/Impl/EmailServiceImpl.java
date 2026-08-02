package com.smpt.email.service.Impl;

import com.smpt.email.dtos.ApiResponse;
import com.smpt.email.dtos.EmailRequest;
import com.smpt.email.entity.Email;
import com.smpt.email.enums.EmailStatus;
import com.smpt.email.repository.EmailRepository;
import com.smpt.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<EmailRequest> sendEmail(EmailRequest emailRequest) {
        Email email = modelMapper.map(emailRequest, Email.class);
        email.setRecipient(emailRequest.getTo());
        email.setStatus(EmailStatus.PENDING);
        Email savedEmail = emailRepository.save(email);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(savedEmail.getRecipient());
            message.setSubject(savedEmail.getSubject());
            message.setText(savedEmail.getBody());
            mailSender.send(message);

            savedEmail.setStatus(EmailStatus.SENT);
            emailRepository.save(savedEmail);

            log.info("Email sent successfully. emailId={}, recipient={}", savedEmail.getId(), savedEmail.getRecipient());

            return ApiResponse.<EmailRequest>builder()
                    .statusCode(200)
                    .message("Email sent successfully")
                    .data(emailRequest)
                    .build();
        } catch (MailException ex) {
            savedEmail.setStatus(EmailStatus.FAILED);
            emailRepository.save(savedEmail);

            log.error("Failed to send email. emailId={}, recipient={}", savedEmail.getId(), savedEmail.getRecipient(), ex);

            return ApiResponse.<EmailRequest>builder()
                    .statusCode(200)
                    .message("Failed to send email")
                    .data(null)
                    .build();
        }
    }
}

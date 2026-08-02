package com.smpt.email.entity;


import com.smpt.email.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emails")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private String subject;
    @Column(length = 1000, nullable = false)
    private String body;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;

}

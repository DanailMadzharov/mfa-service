package com.nexo.mfa.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.nexo.mfa.core.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String emailAddress, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setSubject("Multi-Factor Authentication Code");
        message.setText("Please verify the following code: " + code);
        javaMailSender.send(message);
        log.info(String.format("Email sent successfully to: %s", emailAddress));
    }

}

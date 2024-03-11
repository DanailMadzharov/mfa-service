package com.nexo.mfa.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    private static final String SOME_EMAIL = "email@someMail.com";
    private static final String SOME_CODE = "123456";
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    public void sendEmail_ShouldSendExpectedEmail() {
        ArgumentCaptor<SimpleMailMessage> messageCaptor = forClass(SimpleMailMessage.class);

        emailService.sendEmail(SOME_EMAIL, SOME_CODE);

        verify(javaMailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(SOME_EMAIL, Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals("Multi-Factor Authentication Code", sentMessage.getSubject());
        assertEquals("Please verify the following code: " + SOME_CODE, sentMessage.getText());

    }

}
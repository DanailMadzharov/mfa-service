package com.nexo.mfa.core.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nexo.mfa.core.config.EmailMfaProperties;
import com.nexo.mfa.core.domain.MfaType;
import com.nexo.mfa.core.domain.exception.BadRequestException;
import com.nexo.mfa.core.repository.OneTimePasswordRepository;
import com.nexo.mfa.core.service.EmailService;
import com.nexo.mfa.core.service.generator.SecretCodeGenerator;
import com.nexo.mfa.core.util.HashUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmailStrategyTest {

    private static final String SOME_EMAIL = "email@someMail.com";
    private static final String SOME_CODE = "ААА333";
    @Mock
    private SecretCodeGenerator codeGenerator;

    @Mock
    private EmailMfaProperties emailMfaProperties;

    @Mock
    private OneTimePasswordRepository oneTimePasswordRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailStrategy emailStrategy;

    @Test
    public void handle_ShouldGenerateAndSaveOtpAndSendEmail() {
        when(codeGenerator.generateRandomCode(6)).thenReturn(SOME_CODE);
        when(emailMfaProperties.getLength()).thenReturn(6);
        when(emailMfaProperties.getExpiryMinutes()).thenReturn(5);

        emailStrategy.handle(SOME_EMAIL);

        verify(codeGenerator).generateRandomCode(6);
        verify(oneTimePasswordRepository).save(any());
        verify(emailService).sendEmail(SOME_EMAIL, SOME_CODE);
        verify(oneTimePasswordRepository).deleteByIdentifier(HashUtil.hash(SOME_EMAIL));
    }

    @Test
    public void verify_WithValidCode_ShouldPassVerification() {
        when(oneTimePasswordRepository.deleteOneTimePassword(anyString(), anyString())).thenReturn(1);

        assertDoesNotThrow(() -> emailStrategy.verify(SOME_EMAIL, SOME_CODE));
    }

    @Test
    void verify_WithInvalidCode_ShouldThrowException() {
        when(oneTimePasswordRepository.deleteOneTimePassword(anyString(), anyString())).thenReturn(0);

        assertThrows(BadRequestException.class, () -> emailStrategy.verify(anyString(), anyString()));
    }

    @Test
    void supports_withValidType_returnTrue() {
        assertTrue(emailStrategy.supports(MfaType.EMAIL));
    }

    @Test
    void supports_withInvalid_returnTrue() {
        assertFalse(emailStrategy.supports(MfaType.SMS));
    }

}
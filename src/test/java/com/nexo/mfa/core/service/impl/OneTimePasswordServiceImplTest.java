package com.nexo.mfa.core.service.impl;

import static com.nexo.mfa.core.domain.MfaType.EMAIL;
import static com.nexo.mfa.core.domain.MfaType.SMS;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.stream.Stream;

import com.nexo.mfa.core.domain.exception.MfaNotSupportedException;
import com.nexo.mfa.core.service.OneTimePasswordService;
import com.nexo.mfa.core.service.strategy.MfaStrategy;
import com.nexo.mfa.web.rest.resource.OneTimePasswordResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OneTimePasswordServiceImplTest {

    private static final String VERIFICATION_SUCCESSFUL_MESSAGE = "Verification successful";
    private static final String VERIFICATION_EXPECTED_MESSAGE = "A verification code has been sent";
    private static final String SOME_EMAIL = "email@someMail.com";
    private static final String SOME_CODE = "ААА333";
    @Mock
    private List<MfaStrategy> mfaStrategies;

    @InjectMocks
    private OneTimePasswordServiceImpl oneTimePasswordService;

    @Test
    public void create_WithValidMfaType_ShouldReturnExpectedMessage() {
        MfaStrategy mockStrategy = mock(MfaStrategy.class);
        Mockito.when(mockStrategy.supports(EMAIL)).thenReturn(true);
        Mockito.when(mfaStrategies.stream()).thenReturn(Stream.of(mockStrategy));

        OneTimePasswordResponse response = oneTimePasswordService.create(SOME_EMAIL, EMAIL);

        Assertions.assertEquals(VERIFICATION_EXPECTED_MESSAGE, response.getMessage());
        Mockito.verify(mockStrategy).handle(SOME_EMAIL);
    }

    @Test
    public void verify_WithValidCode_ShouldReturnSuccessMessage() {
        MfaStrategy mockStrategy = mock(MfaStrategy.class);
        Mockito.when(mockStrategy.supports(EMAIL)).thenReturn(true);
        Mockito.when(mfaStrategies.stream()).thenReturn(Stream.of(mockStrategy));

        OneTimePasswordResponse response = oneTimePasswordService.verify(SOME_EMAIL, SOME_CODE, EMAIL);

        Assertions.assertEquals(VERIFICATION_SUCCESSFUL_MESSAGE, response.getMessage());
        Mockito.verify(mockStrategy).verify(SOME_EMAIL, SOME_CODE);
    }

    @Test
    public void create_WithUnsupportedMfaType_ShouldThrowException() {
        Mockito.when(mfaStrategies.stream()).thenReturn(Stream.empty());

        Assertions.assertThrows(MfaNotSupportedException.class, () ->
                oneTimePasswordService.create(SOME_EMAIL, SMS));
    }
}
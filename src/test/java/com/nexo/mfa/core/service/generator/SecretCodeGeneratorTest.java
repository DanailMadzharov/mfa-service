package com.nexo.mfa.core.service.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecretCodeGeneratorTest {

    private final SecretCodeGenerator secretCodeGenerator = new SecretCodeGenerator();

    @Test
    void generateRandomCode_ShouldReturnCorrectLength() {
        int codeLength = 6;
        String code = secretCodeGenerator.generateRandomCode(codeLength);

        assertNotNull(code);
        assertEquals(codeLength, code.length());
    }

    @Test
    void generateRandomCode_ShouldContainOnlyAlphanumericCharacters() {
        int codeLength = 6;
        String code = secretCodeGenerator.generateRandomCode(codeLength);
        String alphanumericRegex = "^[A-Za-z0-9]+$";

        assertTrue(code.matches(alphanumericRegex));
    }

}
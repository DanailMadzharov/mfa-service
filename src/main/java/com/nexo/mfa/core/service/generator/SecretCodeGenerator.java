package com.nexo.mfa.core.service.generator;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class SecretCodeGenerator {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public String generateRandomCode(int codeLength) {
        return IntStream.range(0, codeLength)
                .map(i -> random.nextInt(ALPHANUMERIC_STRING.length()))
                .mapToObj(ALPHANUMERIC_STRING::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

}

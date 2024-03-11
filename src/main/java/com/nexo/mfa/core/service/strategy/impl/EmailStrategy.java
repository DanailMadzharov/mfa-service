package com.nexo.mfa.core.service.strategy.impl;

import static com.nexo.mfa.core.domain.MfaType.EMAIL;
import static com.nexo.mfa.core.domain.exception.errorcode.ErrorCodes.BAD_REQUEST_VERIFICATION_INVALID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;

import com.nexo.mfa.core.config.EmailMfaProperties;
import com.nexo.mfa.core.domain.MfaType;
import com.nexo.mfa.core.domain.OneTimePassword;
import com.nexo.mfa.core.domain.exception.BadRequestException;
import com.nexo.mfa.core.repository.OneTimePasswordRepository;
import com.nexo.mfa.core.service.EmailService;
import com.nexo.mfa.core.service.generator.SecretCodeGenerator;
import com.nexo.mfa.core.service.strategy.MfaStrategy;
import com.nexo.mfa.core.util.HashUtil;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailStrategy implements MfaStrategy {

    private final SecretCodeGenerator codeGenerator;
    private final EmailMfaProperties emailMfaProperties;
    private final OneTimePasswordRepository oneTimePasswordRepository;
    private final EmailService emailService;

    @Override
    public void handle(String emailAddress) {
        String code = codeGenerator.generateRandomCode(emailMfaProperties.getLength()).toUpperCase();

        oneTimePasswordRepository.save(
                OneTimePassword.builder().code(HashUtil.hash(code)).userIdentifier(HashUtil.hash(emailAddress))
                        .expiryDate(OffsetDateTime.now().plusMinutes(emailMfaProperties.getExpiryMinutes())
                                .toLocalDateTime()).build());

        emailService.sendEmail(emailAddress, code);
    }

    @Override
    public void verify(String identifier, String code) {
        if (isVerified(HashUtil.hash(identifier), HashUtil.hash(code))) {
            log.info(String.format("Successful identification: %s", identifier));
        } else {
            throw new BadRequestException(BAD_REQUEST_VERIFICATION_INVALID);
        }
    }

    @Override
    public boolean supports(MfaType type) {
        return EMAIL.equals(type);
    }

    private boolean isVerified(String identifier, String code) {
        return oneTimePasswordRepository.deleteOneTimePassword(identifier, code) > 0;
    }

}

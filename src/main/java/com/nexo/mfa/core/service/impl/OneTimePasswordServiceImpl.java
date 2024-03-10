package com.nexo.mfa.core.service.impl;

import static com.nexo.mfa.core.domain.exception.errorcode.ErrorCodes.MFA_NOT_SUPPORTED;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.nexo.mfa.core.domain.MfaType;
import com.nexo.mfa.core.domain.exception.MfaNotSupportedException;
import com.nexo.mfa.core.service.OneTimePasswordService;
import com.nexo.mfa.core.service.strategy.MfaStrategy;
import com.nexo.mfa.web.rest.resource.OneTimePasswordResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OneTimePasswordServiceImpl implements OneTimePasswordService {

    private static final String VERIFICATION_SUCCESSFUL_MESSAGE = "Verification successful";
    private static final String VERIFICATION_EXPECTED_MESSAGE = "A verification code has been sent";
    private final List<MfaStrategy> mfaStrategies;

    @Override
    public OneTimePasswordResponse create(String userIdentifier, MfaType mfaType) {
        getMfaStrategy(mfaType).handle(userIdentifier);

        return OneTimePasswordResponse.builder().message(VERIFICATION_EXPECTED_MESSAGE).build();
    }

    @Override
    public OneTimePasswordResponse verify(String identifier, String code, MfaType mfaType) {
        getMfaStrategy(mfaType).verify(identifier, code);

        return OneTimePasswordResponse.builder().message(VERIFICATION_SUCCESSFUL_MESSAGE).build();
    }

    private MfaStrategy getMfaStrategy(MfaType mfaType) {
        return mfaStrategies.stream().filter(mfaStrategy -> mfaStrategy.supports(mfaType)).findFirst()
                .orElseThrow(() -> new MfaNotSupportedException(MFA_NOT_SUPPORTED));
    }

}

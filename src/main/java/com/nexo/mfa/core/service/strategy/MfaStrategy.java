package com.nexo.mfa.core.service.strategy;

import com.nexo.mfa.core.domain.MfaType;

public interface MfaStrategy {

    void handle(String identifier);

    void verify(String identifier, String code);

    boolean supports(MfaType type);

}

package com.nexo.mfa.core.service;

import com.nexo.mfa.core.domain.MfaType;
import com.nexo.mfa.web.rest.resource.OneTimePasswordResponse;

public interface OneTimePasswordService {

    OneTimePasswordResponse create(String data, MfaType type);

    OneTimePasswordResponse verify(String identifier, String code, MfaType mfaType);

}

package com.nexo.mfa.core.domain.exception;

import com.nexo.mfa.core.domain.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public class MfaNotSupportedException extends MfaException {

    public MfaNotSupportedException(ErrorCode errorCode) {
        super(errorCode.getMessage(), HttpStatus.BAD_REQUEST.value(), errorCode.getCode());
    }

}

package com.nexo.mfa.core.domain.exception;

import com.nexo.mfa.core.domain.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public class BadRequestException extends MfaException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage(), HttpStatus.BAD_REQUEST.value(), errorCode.getCode());
    }

}

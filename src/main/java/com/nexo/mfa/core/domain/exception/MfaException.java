package com.nexo.mfa.core.domain.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MfaException extends RuntimeException {

    private String code;
    private int httpStatus;

    public MfaException(String message, int httpStatus, String code) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

}


package com.nexo.mfa.core.domain.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCodes implements ErrorCode {

    INTERNAL_ERROR("0001", "An Internal Error Occurred"),
    BAD_REQUEST("0002", "A Bad Request Occurred"),
    NOT_FOUND("0003", "Requested Resource Not Found"),
    INVALID_REQUEST_BODY("0004", "Request Contains Errors"),
    REQUEST_NOT_PARSEABLE("0005", "Request Not In Correct Format"),
    MISSING_REQUEST_ARGUMENTS("0006", "Required Request Arguments Are Not Present"),
    API_NOT_FOUND("0007", "The Requested API Is Not Found"),
    MFA_NOT_SUPPORTED("0008", "Mfa is not supported"),
    BAD_REQUEST_VERIFICATION_INVALID("0009", "Verification is invalid");

    private final String code;
    private final String message;
}

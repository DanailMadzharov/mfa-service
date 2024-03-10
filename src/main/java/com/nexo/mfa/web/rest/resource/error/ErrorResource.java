package com.nexo.mfa.web.rest.resource.error;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nexo.mfa.core.domain.exception.errorcode.ErrorCode;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource {

    private String code;
    private String message;
    private List<FieldErrorResource> filedErrors;

    public ErrorResource(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public ErrorResource(String code, String message) {
        this(code, message, null);
    }

    public ErrorResource(ErrorCode errorCode, List<FieldErrorResource> filedErrors) {
        this(errorCode.getCode(), errorCode.getMessage(), filedErrors);
    }

    public ErrorResource(String code, String message, List<FieldErrorResource> filedErrors) {
        this.message = message;
        this.code = code;
        this.filedErrors = filedErrors;
    }

}

package com.nexo.mfa.web.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexo.mfa.core.domain.exception.MfaException;
import com.nexo.mfa.core.domain.exception.errorcode.ErrorCodes;
import com.nexo.mfa.web.rest.resource.error.ErrorResource;
import com.nexo.mfa.web.rest.resource.error.FieldErrorResource;
import com.nexo.mfa.web.rest.util.ErrorHandlingUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequiredArgsConstructor
public class CustomControllerAdvice {

    private final ObjectMapper objectMapper;

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResource handleException(MissingServletRequestParameterException ex) {
        ErrorHandlingUtil.logException(ex);
        return new ErrorResource(ErrorCodes.MISSING_REQUEST_ARGUMENTS);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResource handleException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processErrors(fieldErrors);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResource handleException(ConstraintViolationException ex) {
        ErrorHandlingUtil.logException(ex);
        return new ErrorResource(ErrorCodes.INVALID_REQUEST_BODY);
    }

    @ExceptionHandler(MfaException.class)
    public void handleMfaException(MfaException ex, HttpServletResponse httpServletResponse)
            throws IOException {
        ErrorHandlingUtil.handleMfaException(httpServletResponse, ex, objectMapper);
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResource handleException(HttpMessageNotReadableException ex) {
        ErrorHandlingUtil.logException(ex);
        return new ErrorResource(ErrorCodes.REQUEST_NOT_PARSEABLE);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ErrorResource handleException(Exception ex) {
        ErrorHandlingUtil.logException(ex);
        return new ErrorResource(ErrorCodes.INTERNAL_ERROR);
    }

    private ErrorResource processErrors(List<FieldError> fieldErrors) {
        List<FieldErrorResource> filedErrorResources = fieldErrors.stream()
                .map(fe -> new FieldErrorResource(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResource(ErrorCodes.INVALID_REQUEST_BODY, filedErrorResources);
    }

}

package com.nexo.mfa.web.rest.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexo.mfa.core.domain.exception.MfaException;
import com.nexo.mfa.web.rest.resource.error.ErrorResource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorHandlingUtil {

    public static final String ERROR_MESSAGE = "Exception Message: [{}], Detailed message: [{}], Exception cause: [{}]";
    public static final String ERROR_MESSAGE_EXP = "Exception Message: [{}], Exception cause: [{}]";

    public static void handleMfaException(HttpServletResponse response, MfaException mfaException,
            ObjectMapper objectMapper)
            throws IOException {
        logException(mfaException);

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(mfaException.getHttpStatus());
        objectMapper.writeValue(response.getWriter(), toErrorResource(mfaException));
        response.getWriter().close();
    }

    public static void logException(Throwable exception) {
        if (exception instanceof MfaException) {
            log.error(ERROR_MESSAGE, exception.getMessage(), exception.getMessage(),
                    getCause(exception));
        } else {
            log.error(ERROR_MESSAGE_EXP, exception.getMessage(), getCause(exception), exception);
        }
    }

    private static String getCause(Throwable exception) {
        if (exception.getCause() == null) {
            return exception.getClass().getSimpleName();
        } else {
            return exception.getCause().getClass().getSimpleName();
        }
    }

    private static ErrorResource toErrorResource(MfaException mfaException) {
        return new ErrorResource(mfaException.getCode(), mfaException.getMessage());
    }

}

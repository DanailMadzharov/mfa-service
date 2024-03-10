package com.nexo.mfa.web.rest.resource.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FieldErrorResource {

    private String field;
    private String message;

}

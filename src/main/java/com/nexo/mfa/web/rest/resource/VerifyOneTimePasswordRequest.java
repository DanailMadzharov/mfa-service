package com.nexo.mfa.web.rest.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VerifyOneTimePasswordRequest extends OneTimePasswordRequest {

    @NotBlank
    @Size(max = 6)
    private String code;

}

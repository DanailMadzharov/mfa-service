package com.nexo.mfa.web.rest.resource;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class OneTimePasswordRequest {

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

}
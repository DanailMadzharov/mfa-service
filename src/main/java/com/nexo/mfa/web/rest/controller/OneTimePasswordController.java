package com.nexo.mfa.web.rest.controller;

import static com.nexo.mfa.core.domain.MfaType.EMAIL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.nexo.mfa.core.service.OneTimePasswordService;
import com.nexo.mfa.web.rest.resource.OneTimePasswordRequest;
import com.nexo.mfa.web.rest.resource.OneTimePasswordResponse;
import com.nexo.mfa.web.rest.resource.VerifyOneTimePasswordRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/otp")
public class OneTimePasswordController {

    private final OneTimePasswordService oneTimePasswordService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public OneTimePasswordResponse create(@RequestBody @Validated OneTimePasswordRequest oneTimePasswordRequest) {
        return oneTimePasswordService.create(oneTimePasswordRequest.getEmail(), EMAIL);
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public OneTimePasswordResponse create(@RequestBody @Validated VerifyOneTimePasswordRequest verifyRequest) {
        return oneTimePasswordService.verify(verifyRequest.getEmail(), verifyRequest.getCode().toUpperCase(), EMAIL);
    }

}

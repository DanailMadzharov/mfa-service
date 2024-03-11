package com.nexo.mfa.web.rest.controller;

import static com.nexo.mfa.core.domain.MfaType.EMAIL;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexo.mfa.core.service.impl.OneTimePasswordServiceImpl;
import com.nexo.mfa.web.rest.resource.OneTimePasswordRequest;
import com.nexo.mfa.web.rest.resource.OneTimePasswordResponse;
import com.nexo.mfa.web.rest.resource.VerifyOneTimePasswordRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(OneTimePasswordController.class)
public class OneTimePasswordControllerTest {

    private static final String SOME_EMAIL = "email@someMail.com";
    private static final String SOME_CODE = "ААА333";
    private static final String VERIFICATION_CODE_SENT = "A verification code has been sent";
    private static final String VERIFICATION_SUCCESSFUL = "Verification successful";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OneTimePasswordServiceImpl oneTimePasswordService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void create_WithValidRequest_ShouldReturnOk() throws Exception {
        OneTimePasswordRequest request = new OneTimePasswordRequest();
        request.setEmail(SOME_EMAIL);
        OneTimePasswordResponse response = OneTimePasswordResponse.builder()
                .message(VERIFICATION_CODE_SENT)
                .build();
        when(oneTimePasswordService.create(SOME_EMAIL, EMAIL)).thenReturn(response);

        mockMvc.perform(post("/v1/otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(VERIFICATION_CODE_SENT));

        verify(oneTimePasswordService, times(1)).create(SOME_EMAIL, EMAIL);
    }

    @Test
    public void verify_WithValidRequest_ShouldReturnOk() throws Exception {
        VerifyOneTimePasswordRequest request = new VerifyOneTimePasswordRequest();
        request.setEmail(SOME_EMAIL);
        request.setCode(SOME_CODE);
        OneTimePasswordResponse response = OneTimePasswordResponse.builder()
                .message(VERIFICATION_SUCCESSFUL)
                .build();

        when(oneTimePasswordService.verify(SOME_EMAIL, SOME_CODE, EMAIL)).thenReturn(response);

        mockMvc.perform(post("/v1/otp/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(VERIFICATION_SUCCESSFUL));

        verify(oneTimePasswordService, times(1)).verify(SOME_EMAIL, SOME_CODE, EMAIL);
    }

}
package com.nexo.mfa.core.service;

public interface EmailService {
    void sendEmail(String emailAddress, String code);

}

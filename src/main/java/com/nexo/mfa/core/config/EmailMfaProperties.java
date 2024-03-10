package com.nexo.mfa.core.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("code.email")
public class EmailMfaProperties {

    private Integer length;
    private Integer expiryMinutes;

}

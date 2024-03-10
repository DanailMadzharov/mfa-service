package com.nexo.mfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MfaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MfaServiceApplication.class, args);
    }

}

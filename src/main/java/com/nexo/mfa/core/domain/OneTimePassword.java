package com.nexo.mfa.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(schema = "MFA", name = "ONE_TIME_PASSWORD")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OneTimePassword {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String code;
    private LocalDateTime expiryDate;
    private String userIdentifier;

}

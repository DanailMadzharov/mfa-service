--liquibase formatted sql

--changeset danail_madzharov:init

CREATE TABLE mfa.one_time_password
(
    id              VARCHAR(36) primary key,
    code            VARCHAR(65)  not null,
    user_identifier VARCHAR(120) not null,
    expiry_date     DATETIME     not null
);

CREATE TABLE mfa.shedlock
(
    name       VARCHAR(64),
    lock_until TIMESTAMP(3) NULL,
    locked_at  TIMESTAMP(3) NULL,
    locked_by  VARCHAR(255),
    PRIMARY KEY (name)
);

--rollback DROP TABLE mfa.one_time_password;

--rollback DROP TABLE mfa.shedlock;
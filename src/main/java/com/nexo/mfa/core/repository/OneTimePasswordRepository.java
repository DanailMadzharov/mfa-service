package com.nexo.mfa.core.repository;

import java.time.LocalDateTime;

import com.nexo.mfa.core.domain.OneTimePassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OneTimePasswordRepository extends JpaRepository<OneTimePassword, String> {

    @Transactional
    @Modifying
    @Query("DELETE FROM OneTimePassword otp " +
            "WHERE otp.code = :code " +
            "AND otp.userIdentifier = :userIdentifier")
    int deleteOneTimePassword(@Param("userIdentifier") String userIdentifier, @Param("code") String code);

    @Transactional
    @Modifying
    @Query("DELETE FROM OneTimePassword otp " +
            "WHERE otp.expiryDate <= :now")
    void expireOneTimePasswords(@Param("now") LocalDateTime now);

    @Transactional
    @Modifying
    @Query("DELETE FROM OneTimePassword otp " +
            "WHERE otp.userIdentifier = :hashedIdentifier")
    void deleteByIdentifier(String hashedIdentifier);

}

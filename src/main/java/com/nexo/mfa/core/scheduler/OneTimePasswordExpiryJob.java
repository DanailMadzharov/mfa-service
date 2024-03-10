package com.nexo.mfa.core.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import com.nexo.mfa.core.repository.OneTimePasswordRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OneTimePasswordExpiryJob {

    private final OneTimePasswordRepository oneTimePasswordRepository;

    @Scheduled(timeUnit = TimeUnit.MINUTES, fixedRate = 1)
    @SchedulerLock(name = "expire_codes", lockAtMostFor = "1m")
    public void handleExpiredCodes() {
        oneTimePasswordRepository.expireOneTimePasswords(OffsetDateTime.now().toLocalDateTime());
        log.info("Successfully cleared expired codes");
    }

}

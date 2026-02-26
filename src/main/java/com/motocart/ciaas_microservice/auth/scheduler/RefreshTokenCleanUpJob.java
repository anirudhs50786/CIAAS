package com.motocart.ciaas_microservice.auth.scheduler;

import com.motocart.ciaas_microservice.auth.repository.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RefreshTokenCleanUpJob {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenCleanUpJob(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanUpExpiredTokens() {
        refreshTokenRepository.deleteInvalidTokens(Instant.now());
    }
}

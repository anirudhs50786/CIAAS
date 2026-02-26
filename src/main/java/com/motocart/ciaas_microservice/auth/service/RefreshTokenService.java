package com.motocart.ciaas_microservice.auth.service;

import com.motocart.ciaas_microservice.auth.entity.RefreshTokenEntity;
import com.motocart.ciaas_microservice.auth.repository.RefreshTokenRepository;
import com.motocart.ciaas_microservice.auth.vo.JwtVO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Getter
@Service
public class RefreshTokenService {

    @Value("${jwt.expiration.refresh-token}")
    private int refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateRefreshToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomNumbers = new byte[32];
        secureRandom.nextBytes(randomNumbers);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomNumbers);
    }

    public void processTokenRefresh(JwtVO jwtVO, String previousRefreshToken) {
        revokeRefreshToken(jwtVO, previousRefreshToken);
        saveRefreshToken(jwtVO);
    }

    private void revokeRefreshToken(JwtVO jwtVO, String previousRefreshToken) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository
                .findByUserIdAndRefreshTokenHash(jwtVO.getUserId(), hashRefreshToken(previousRefreshToken))
                .orElse(null);
        if (refreshTokenEntity == null) {
            return;
        }
        refreshTokenEntity.setRevoked(true);
        refreshTokenRepository.save(refreshTokenEntity);
    }

    private String hashRefreshToken(String refreshToken) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(refreshToken.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("SHA-256 Algorithm not found.", exception);
        }
    }

    public void saveRefreshToken(JwtVO jwtVO) {
        Instant refreshTokenExpiresAt = Instant.now().plusMillis(jwtVO.getRefreshTokenExpiresIn());
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder()
                .userId(jwtVO.getUserId())
                .refreshTokenHash(hashRefreshToken(jwtVO.getRefreshToken()))
                .refreshTokenExpiresAt(refreshTokenExpiresAt)
                .isRevoked(false)
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    public void validateRefreshToken(int userId, String refreshToken) {
        RefreshTokenEntity entity = refreshTokenRepository
                .findByUserIdAndRefreshTokenHash(userId, hashRefreshToken(refreshToken))
                .orElseThrow(() -> new BadCredentialsException("Invalid refresh token"));

        if (entity.isRevoked() || entity.getRefreshTokenExpiresAt().isBefore(Instant.now())) {
            throw new BadCredentialsException("Refresh token expired or revoked");
        }
    }
}

package com.motocart.ciaas_microservice.auth.repository;

import com.motocart.ciaas_microservice.auth.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    @Query(value = "SELECT * FROM refresh_tokens WHERE user_id = :userId AND refresh_token_hash = :refreshTokenHash", nativeQuery = true)
    Optional<RefreshTokenEntity> findByUserIdAndRefreshTokenHash(@Param("userId") int userId, @Param("refreshTokenHash") String refreshTokenHash);

    @Query(value = "DELETE FROM refresh_tokens WHERE is_revoked =  true or refresh_token_expires_at < :expiryTime", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteInvalidTokens(@Param("expiryTime") Instant expiryTime);
}

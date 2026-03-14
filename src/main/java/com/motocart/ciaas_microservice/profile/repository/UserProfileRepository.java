package com.motocart.ciaas_microservice.profile.repository;

import com.motocart.ciaas_microservice.profile.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Integer> {

    @Query(value = "SELECT * FROM user_profile WHERE user_id = :userId", nativeQuery = true)
    Optional<UserProfileEntity> findByUserId(@Param("userId") int userId);
}

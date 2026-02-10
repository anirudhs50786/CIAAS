package com.motocart.ciaas_microservice.auth.repository;

import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(value = "SELECT * FROM users where user_name = :userName", nativeQuery = true)
    Optional<UserEntity> findByUserName(@Param("userName") String userName);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE email = :email)", nativeQuery = true)
    boolean existsByEmail(@Param("email") String email);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM users WHERE user_name = :userName)", nativeQuery = true)
    boolean existsByUserName(@Param("userName") String userName);
}

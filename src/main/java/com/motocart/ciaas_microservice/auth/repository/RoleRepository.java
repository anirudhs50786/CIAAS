package com.motocart.ciaas_microservice.auth.repository;

import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Query(value = "SELECT * FROM roles where authority = :authority", nativeQuery = true)
    Optional<RoleEntity> findByAuthority(@Param("authority") String authority);
}

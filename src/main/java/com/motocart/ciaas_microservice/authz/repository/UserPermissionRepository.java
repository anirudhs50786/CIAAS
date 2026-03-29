package com.motocart.ciaas_microservice.authz.repository;

import com.motocart.ciaas_microservice.authz.entity.UserPermissionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserPermissionRepository extends JpaRepository<UserPermissionEntity, Integer> {

    @EntityGraph(attributePaths = {"permission"})
    Set<UserPermissionEntity> findAllByUserId(int userId);
}

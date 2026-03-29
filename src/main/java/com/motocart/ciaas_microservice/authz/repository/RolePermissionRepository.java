package com.motocart.ciaas_microservice.authz.repository;

import com.motocart.ciaas_microservice.authz.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Integer> {

}

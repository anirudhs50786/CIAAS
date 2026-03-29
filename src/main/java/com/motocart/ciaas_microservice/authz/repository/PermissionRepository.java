package com.motocart.ciaas_microservice.authz.repository;

import com.motocart.ciaas_microservice.authz.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {

    @Query(value = """
            SELECT DISTINCT p.permission_name
            FROM permissions p
            INNER JOIN role_permissions rp ON p.permission_id = rp.permission_id
            INNER JOIN roles r ON rp.role_id = r.role_id
            WHERE r.role_name IN (:roles)
            """, nativeQuery = true)
    Set<String> findAllByRoles(@Param("roles")  Set<String> roles);
}

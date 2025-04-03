package com.motocart.ciaas_microservice.dao;

import com.motocart.ciaas_microservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority(String authority);
}

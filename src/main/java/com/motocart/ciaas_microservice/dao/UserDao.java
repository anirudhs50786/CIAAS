package com.motocart.ciaas_microservice.dao;

import com.motocart.ciaas_microservice.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<ApplicationUser, Integer> {

    Optional<ApplicationUser> findByUserName(String userName);
}

package com.motocart.ciaas_microservice.profile.repository;

import com.motocart.ciaas_microservice.profile.entity.UserAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, Integer> {
}

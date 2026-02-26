package com.motocart.ciaas_microservice.profile.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "user_profile")
@Data
@Builder
public class UserProfileEntity {
}

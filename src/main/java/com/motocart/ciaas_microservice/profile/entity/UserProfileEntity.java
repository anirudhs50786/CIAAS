package com.motocart.ciaas_microservice.profile.entity;

import com.motocart.ciaas_microservice.types.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "user_profile")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private int userProfileId;

    @Column(name = "user_id", unique = true, updatable = false)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Profile.Gender gender;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_profile_id")
    private List<UserAddressEntity> deliveryAddresses;
}

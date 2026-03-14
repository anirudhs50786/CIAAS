package com.motocart.ciaas_microservice.profile.entity;

import com.motocart.library.common.types.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "user_address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_address_id")
    private int userAddressId;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private Profile.AddressType addressType;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private Profile.Country country;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "created_at")
    private Instant createdAt;


}

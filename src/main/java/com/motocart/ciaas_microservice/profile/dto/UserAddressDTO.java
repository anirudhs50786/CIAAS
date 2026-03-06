package com.motocart.ciaas_microservice.profile.dto;

import com.motocart.ciaas_microservice.types.Profile;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class UserAddressDTO implements Serializable {

    private int userAddressId;
    private String addressLine;
    private String landmark;
    private String cityName;
    private String zipCode;
    private String state;
    private Profile.AddressType addressType;
    private Profile.Country country;
    private boolean isDefault;
    private Instant createdAt;
}

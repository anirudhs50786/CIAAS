package com.motocart.ciaas_microservice.profile.api;

import com.motocart.ciaas_microservice.profile.dto.UserProfileDTO;
import org.springframework.http.ResponseEntity;

public interface UserProfileResource {

    UserProfileDTO getUserProfile();

    UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO);

}

package com.motocart.ciaas_microservice.profile.api;

import com.motocart.library.common.dto.UserProfileDTO;

public interface UserProfileResource {

    UserProfileDTO getUserProfile();

    UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO);

}

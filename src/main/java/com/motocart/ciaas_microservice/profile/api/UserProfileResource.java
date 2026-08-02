package com.motocart.ciaas_microservice.profile.api;

import com.motocart.library.common.dto.request.UserProfileRequestDTO;
import com.motocart.library.common.dto.response.UserProfileResponseDTO;

public interface UserProfileResource {

    UserProfileResponseDTO getUserProfile();

    UserProfileResponseDTO createUserProfile(UserProfileRequestDTO userProfileDTO);

}

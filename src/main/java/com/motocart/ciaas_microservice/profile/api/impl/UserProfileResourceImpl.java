package com.motocart.ciaas_microservice.profile.api.impl;

import com.motocart.ciaas_microservice.profile.api.UserProfileResource;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.ciaas_microservice.profile.service.UserProfileService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import com.motocart.library.common.dto.request.UserProfileRequestDTO;
import com.motocart.library.common.dto.response.UserProfileResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@MotocartAPI("/profile")
public class UserProfileResourceImpl implements UserProfileResource {

    private final UserProfileService userProfileService;

    public UserProfileResourceImpl(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    @GetMapping
    public UserProfileResponseDTO getUserProfile() {
        return MapperUtil.toUserProfileDTO(
                userProfileService.getUserProfile().orElseThrow(() -> new RuntimeException("User Profile does not exist")));
    }

    @Override
    @PostMapping
    public UserProfileResponseDTO createUserProfile(@Valid @RequestBody UserProfileRequestDTO userProfileDTO) {
        return MapperUtil.toUserProfileDTO(userProfileService.createUserProfile(userProfileDTO));
    }
}

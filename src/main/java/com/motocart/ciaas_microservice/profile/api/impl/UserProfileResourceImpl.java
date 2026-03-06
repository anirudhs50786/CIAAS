package com.motocart.ciaas_microservice.profile.api.impl;

import com.motocart.ciaas_microservice.profile.api.UserProfileResource;
import com.motocart.ciaas_microservice.profile.dto.UserProfileDTO;
import com.motocart.ciaas_microservice.profile.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class UserProfileResourceImpl implements UserProfileResource {

    private final UserProfileService userProfileService;

    public UserProfileResourceImpl(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Override
    @GetMapping
    public UserProfileDTO getUserProfile() {
        return null;
    }

    @Override
    public UserProfileDTO createUserProfile(UserProfileDTO userProfileDTO) {
        userProfileService.createUserProfile(userProfileDTO);
        return userProfileDTO;
    }
}

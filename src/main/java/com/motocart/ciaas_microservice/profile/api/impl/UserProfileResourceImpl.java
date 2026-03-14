package com.motocart.ciaas_microservice.profile.api.impl;

import com.motocart.ciaas_microservice.profile.api.UserProfileResource;
import com.motocart.ciaas_microservice.profile.dto.UserProfileDTO;
import com.motocart.ciaas_microservice.profile.service.UserProfileService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return MapperUtil.toUserProfileDTO(
                userProfileService.getUserProfile().orElseThrow(() -> new RuntimeException("User Profile does not exist")));
    }

    @Override
    @PostMapping
    public UserProfileDTO createUserProfile(@Valid @RequestBody UserProfileDTO userProfileDTO) {
        userProfileService.createUserProfile(userProfileDTO);
        return userProfileDTO;
    }
}

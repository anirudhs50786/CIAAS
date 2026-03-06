package com.motocart.ciaas_microservice.profile.service;

import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.profile.dto.UserProfileDTO;
import com.motocart.ciaas_microservice.profile.entity.UserAddressEntity;
import com.motocart.ciaas_microservice.profile.entity.UserProfileEntity;
import com.motocart.ciaas_microservice.profile.repository.UserProfileRepository;
import com.motocart.ciaas_microservice.util.MapperUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public void createUserProfile(UserProfileDTO userProfileDTO) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var userAddressEntityList = userProfileDTO.getUserAddressDTOList()
                .stream()
                .map(MapperUtil::toUserAddressEntity)
                .toList();

        UserProfileEntity userProfileEntity = UserProfileEntity.builder()
                .userId(user.getUserId())
                .dateOfBirth(userProfileDTO.getDateOfBirth())
                .profileImageUrl(userProfileDTO.getProfileImageUrl())
                .gender(userProfileDTO.getGender())
                .firstName(userProfileDTO.getFirstName())
                .lastName(userProfileDTO.getLastName())
                .phoneNumber(userProfileDTO.getPhoneNumber())
                .deliveryAddresses(userAddressEntityList)
                .build();
        userProfileRepository.save(userProfileEntity);
    }
}

package com.motocart.ciaas_microservice.profile.service;

import com.motocart.library.common.dto.UserProfileDTO;
import com.motocart.ciaas_microservice.profile.entity.UserProfileEntity;
import com.motocart.ciaas_microservice.profile.repository.UserProfileRepository;
import com.motocart.ciaas_microservice.util.AuthHelper;
import com.motocart.ciaas_microservice.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public Optional<UserProfileEntity> getUserProfile() {
        int userId = AuthHelper.getAuthUserId();
        return userProfileRepository.findByUserId(userId);
    }

    public void createUserProfile(UserProfileDTO userProfileDTO) {
        int userId = AuthHelper.getAuthUserId();

        var userAddressEntityList = userProfileDTO.getUserAddressDTOList()
                .stream()
                .map(MapperUtil::toUserAddressEntity)
                .toList();

        UserProfileEntity userProfileEntity = MapperUtil.toUserProfileEntity(userId, userAddressEntityList, userProfileDTO);
        userProfileRepository.save(userProfileEntity);
    }
}

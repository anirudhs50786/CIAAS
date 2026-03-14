package com.motocart.ciaas_microservice.util;

import com.motocart.library.common.dto.response.AuthenticationResponseDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.vo.JwtVO;
import com.motocart.ciaas_microservice.profile.dto.UserAddressDTO;
import com.motocart.ciaas_microservice.profile.dto.UserProfileDTO;
import com.motocart.ciaas_microservice.profile.entity.UserAddressEntity;
import com.motocart.ciaas_microservice.profile.entity.UserProfileEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public final class MapperUtil {

    public static SignUpResponseDTO toSignUpResponse(UserEntity user) {
        return SignUpResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();
    }

    public static AuthenticationResponseDTO toAuthenticationResponse(JwtVO jwtVO) {
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtVO.getAccessToken())
                .accessTokenExpiresIn(jwtVO.getAccessTokenExpiresIn())
                .refreshToken(jwtVO.getRefreshToken())
                .refreshTokenExpiresIn(jwtVO.getRefreshTokenExpiresIn())
                .build();
    }

    public static UserAddressEntity toUserAddressEntity(UserAddressDTO userAddressDTO) {
        return UserAddressEntity.builder()
                .userAddressId(userAddressDTO.getUserAddressId())
                .addressLine(userAddressDTO.getAddressLine())
                .landmark(userAddressDTO.getLandmark())
                .cityName(userAddressDTO.getCityName())
                .zipCode(userAddressDTO.getZipCode())
                .state(userAddressDTO.getState())
                .addressType(userAddressDTO.getAddressType())
                .country(userAddressDTO.getCountry())
                .isDefault(userAddressDTO.isDefault())
                .createdAt(userAddressDTO.getCreatedAt())
                .build();
    }

    public static String authoritiesToString(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(Object::toString)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    public static UserProfileEntity toUserProfileEntity(int userId, List<UserAddressEntity> userAddressEntityList, UserProfileDTO userProfileDTO) {
        return UserProfileEntity.builder()
                .userId(userId)
                .dateOfBirth(userProfileDTO.getDateOfBirth())
                .profileImageUrl(userProfileDTO.getProfileImageUrl())
                .gender(userProfileDTO.getGender())
                .firstName(userProfileDTO.getFirstName())
                .lastName(userProfileDTO.getLastName())
                .phoneNumber(userProfileDTO.getPhoneNumber())
                .deliveryAddresses(userAddressEntityList)
                .build();
    }

    public static UserProfileDTO toUserProfileDTO(UserProfileEntity userProfileEntity) {
        return UserProfileDTO.builder()
                .dateOfBirth(userProfileEntity.getDateOfBirth())
                .profileImageUrl(userProfileEntity.getProfileImageUrl())
                .gender(userProfileEntity.getGender())
                .firstName(userProfileEntity.getFirstName())
                .lastName(userProfileEntity.getLastName())
                .phoneNumber(userProfileEntity.getPhoneNumber())
                .build();
    }
}

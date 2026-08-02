package com.motocart.ciaas_microservice.util;

import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.vo.JwtVO;
import com.motocart.ciaas_microservice.profile.entity.UserAddressEntity;
import com.motocart.ciaas_microservice.profile.entity.UserProfileEntity;
import com.motocart.ciaas_microservice.types.AccountStatus;
import com.motocart.library.common.dto.UserAddressDTO;
import com.motocart.library.common.dto.UserDetailDTO;
import com.motocart.library.common.dto.UserSummaryDTO;
import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.library.common.dto.request.UserProfileRequestDTO;
import com.motocart.library.common.dto.response.AuthenticationResponseDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;
import com.motocart.library.common.dto.response.UserProfileResponseDTO;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class MapperUtil {

    public static SignUpResponseDTO toSignUpResponse(UserEntity user) {
        return SignUpResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .build();
    }

    public static AuthenticationResponseDTO toAuthenticationResponse(JwtVO jwtVO) {
        return AuthenticationResponseDTO.builder()
                .tokenType("Bearer")
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

    public static UserProfileEntity toUserProfileEntity(int userId, List<UserAddressEntity> userAddressEntityList, UserProfileRequestDTO userProfileDTO) {
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

    public static UserProfileResponseDTO toUserProfileDTO(UserProfileEntity userProfileEntity) {
        return UserProfileResponseDTO.builder()
                .dateOfBirth(userProfileEntity.getDateOfBirth())
                .profileImageUrl(userProfileEntity.getProfileImageUrl())
                .gender(userProfileEntity.getGender())
                .firstName(userProfileEntity.getFirstName())
                .lastName(userProfileEntity.getLastName())
                .phoneNumber(userProfileEntity.getPhoneNumber())
                .build();
    }

    public static UserEntity toUserEntity(SignUpRequestDTO signUpRequestDTO, String encodedPassword, Set<RoleEntity> authorities) {
        return UserEntity.builder()
                .username(signUpRequestDTO.getUsername())
                .email(signUpRequestDTO.getEmail())
                .password(encodedPassword)
                .authorities(authorities)
                .createdOn(Instant.now())
                .accountStatus(AccountStatus.ACTIVE_INCOMPLETE.getStatusCode())
                .build();
    }

    public static List<UserSummaryDTO> toUserSummaryList(List<UserEntity> users) {
        return users.stream()
                .map(u -> UserSummaryDTO.builder()
                        .userId(u.getUserId())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .accountStatus(u.getAccountStatus())
                        .createdOn(u.getCreatedOn())
                        .build())
                .collect(Collectors.toList());
    }

    public static UserDetailDTO toUserDetailDTO(UserEntity user, UserProfileEntity profile) {
        UserDetailDTO.UserDetailDTOBuilder builder = UserDetailDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .accountStatus(user.getAccountStatus())
                .createdOn(user.getCreatedOn());

        if (profile != null) {
            builder.firstName(profile.getFirstName())
                    .lastName(profile.getLastName())
                    .phoneNumber(profile.getPhoneNumber())
                    .gender(profile.getGender())
                    .dateOfBirth(profile.getDateOfBirth())
                    .profileImageUrl(profile.getProfileImageUrl())
                    .deliveryAddresses(profile.getDeliveryAddresses().stream()
                            .map(a -> UserAddressDTO.builder()
                                    .userAddressId(a.getUserAddressId())
                                    .addressLine(a.getAddressLine())
                                    .landmark(a.getLandmark())
                                    .cityName(a.getCityName())
                                    .zipCode(a.getZipCode())
                                    .state(a.getState())
                                    .addressType(a.getAddressType())
                                    .country(a.getCountry())
                                    .isDefault(a.isDefault())
                                    .createdAt(a.getCreatedAt())
                                    .build())
                            .collect(Collectors.toList()));
        }

        return builder.build();
    }
}

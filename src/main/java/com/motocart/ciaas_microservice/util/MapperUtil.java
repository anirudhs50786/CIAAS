package com.motocart.ciaas_microservice.util;

import com.motocart.ciaas_microservice.auth.dto.reponse.AuthenticationResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.vo.JwtVO;

public class MapperUtil {

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
}

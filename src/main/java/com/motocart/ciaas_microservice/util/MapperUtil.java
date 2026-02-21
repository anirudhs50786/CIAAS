package com.motocart.ciaas_microservice.util;

import com.motocart.ciaas_microservice.auth.dto.reponse.SignInResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignInRequestDTO;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;

public class MapperUtil {

    public static SignUpResponseDTO toSignUpResponse(UserEntity user) {
        return SignUpResponseDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUsername())
                .build();
    }

    public static SignInResponseDTO toSignInResponse(String jwtToken, SignInRequestDTO signInRequestDTO) {
        return SignInResponseDTO.builder()
                .jwtToken(jwtToken)
                .username(signInRequestDTO.getLoginId())
                .build();
    }
}

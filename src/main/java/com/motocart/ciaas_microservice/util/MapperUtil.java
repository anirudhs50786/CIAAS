package com.motocart.ciaas_microservice.util;

import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;

public class MapperUtil {

    public static SignUpResponseDTO entityToDto(UserEntity user) {
        SignUpResponseDTO dto = new SignUpResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUsername());
        return dto;
    }
}

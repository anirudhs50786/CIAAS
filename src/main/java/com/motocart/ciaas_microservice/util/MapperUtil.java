package com.motocart.ciaas_microservice.util;

import com.motocart.ciaas_microservice.dto.UserDTO;
import com.motocart.ciaas_microservice.entity.ApplicationUser;

public class MapperUtil {

    public static UserDTO entityToDto(ApplicationUser user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUserName(user.getUsername());
        return dto;
    }
}

package com.motocart.ciaas_microservice.api.rest;

import com.motocart.ciaas_microservice.dto.RegistrationDTO;
import com.motocart.ciaas_microservice.dto.UserDTO;

public interface AuthenticationResource {
    UserDTO registerUser(RegistrationDTO registrationDTO);
}

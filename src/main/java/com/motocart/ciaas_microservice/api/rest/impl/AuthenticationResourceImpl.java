package com.motocart.ciaas_microservice.api.rest.impl;

import com.motocart.ciaas_microservice.api.rest.AuthenticationResource;
import com.motocart.ciaas_microservice.dto.RegistrationDTO;
import com.motocart.ciaas_microservice.dto.UserDTO;
import com.motocart.ciaas_microservice.service.AuthenticationService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationResourceImpl implements AuthenticationResource {

    private final AuthenticationService authenticationService;

    public AuthenticationResourceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return MapperUtil.entityToDto(authenticationService.registerCustomerUser(registrationDTO));
    }
}

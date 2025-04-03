package com.motocart.ciaas_microservice.api.rest.impl;

import com.motocart.ciaas_microservice.api.rest.AuthenticationResource;
import com.motocart.ciaas_microservice.dto.RegistrationDTO;
import com.motocart.ciaas_microservice.entity.ApplicationUser;
import com.motocart.ciaas_microservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationResourceImpl implements AuthenticationResource {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.registerUser(registrationDTO.getUsername(), registrationDTO.getPassword());
    }
}

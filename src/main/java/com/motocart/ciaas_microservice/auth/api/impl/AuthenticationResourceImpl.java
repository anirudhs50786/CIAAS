package com.motocart.ciaas_microservice.auth.api.impl;

import com.motocart.ciaas_microservice.auth.api.AuthenticationResource;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignInResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignInRequestDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;
import com.motocart.ciaas_microservice.auth.service.AuthenticationService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import jakarta.validation.Valid;
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
    public SignUpResponseDTO registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return MapperUtil.entityToDto(authenticationService.registerCustomerUser(signUpRequestDTO));
    }

    @PostMapping("/login")
    public SignInResponseDTO loginUser(@Valid @RequestBody SignInRequestDTO signInRequestDTO) {
        return null;
    }
}

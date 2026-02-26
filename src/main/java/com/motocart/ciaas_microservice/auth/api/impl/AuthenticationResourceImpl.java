package com.motocart.ciaas_microservice.auth.api.impl;

import com.motocart.ciaas_microservice.auth.api.AuthenticationResource;
import com.motocart.ciaas_microservice.auth.dto.reponse.AuthenticationResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignInRequestDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;
import com.motocart.ciaas_microservice.auth.service.AuthenticationService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationResourceImpl implements AuthenticationResource {

    private final AuthenticationService authenticationService;

    public AuthenticationResourceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public SignUpResponseDTO registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return MapperUtil.toSignUpResponse(authenticationService.registerCustomerUser(signUpRequestDTO));
    }

    @PostMapping("/login")
    public AuthenticationResponseDTO loginUser(@Valid @RequestBody SignInRequestDTO signInRequestDTO) {
        return MapperUtil.toAuthenticationResponse(authenticationService.verifyCredentials(signInRequestDTO));
    }

    @PostMapping("/refresh")
    public AuthenticationResponseDTO getNewAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
        return MapperUtil.toAuthenticationResponse(authenticationService.getNewAccessToken(refreshToken));
    }
}

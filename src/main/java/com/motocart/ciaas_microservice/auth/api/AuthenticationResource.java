package com.motocart.ciaas_microservice.auth.api;

import com.motocart.ciaas_microservice.auth.dto.reponse.AuthenticationResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignInRequestDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;

public interface AuthenticationResource {
    SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequestDTO);

    AuthenticationResponseDTO loginUser(SignInRequestDTO signInRequestDTO);

    AuthenticationResponseDTO getNewAccessToken(String refreshToken);
}

package com.motocart.ciaas_microservice.auth.api;

import com.motocart.library.common.dto.response.AuthenticationResponseDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;
import com.motocart.library.common.dto.request.SignInRequestDTO;
import com.motocart.library.common.dto.request.SignUpRequestDTO;

public interface AuthenticationResource {
    SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequestDTO);

    AuthenticationResponseDTO loginUser(SignInRequestDTO signInRequestDTO);

    AuthenticationResponseDTO getNewAccessToken(String refreshToken);
}

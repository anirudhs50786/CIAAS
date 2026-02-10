package com.motocart.ciaas_microservice.auth.api;

import com.motocart.ciaas_microservice.auth.dto.reponse.SignInResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.reponse.SignUpResponseDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignInRequestDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationResource {
    SignUpResponseDTO registerUser(SignUpRequestDTO signUpRequestDTO);

    SignInResponseDTO loginUser(@RequestBody SignInRequestDTO signInRequestDTO);
}

package com.motocart.ciaas_microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public class SignInRequestDTO implements Serializable {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}

package com.motocart.ciaas_microservice.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignInRequestDTO implements Serializable {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}

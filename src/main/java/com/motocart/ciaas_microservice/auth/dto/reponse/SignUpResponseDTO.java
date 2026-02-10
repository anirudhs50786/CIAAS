package com.motocart.ciaas_microservice.auth.dto.reponse;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignUpResponseDTO implements Serializable {

    private int userId;
    private String userName;
}

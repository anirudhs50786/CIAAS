package com.motocart.ciaas_microservice.auth.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDTO implements Serializable {

    private int userId;
    private String userName;
}

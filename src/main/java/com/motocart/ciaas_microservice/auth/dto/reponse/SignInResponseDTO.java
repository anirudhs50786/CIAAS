package com.motocart.ciaas_microservice.auth.dto.reponse;

import com.motocart.ciaas_microservice.types.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class SignInResponseDTO implements Serializable {
    private String username;
    private String jwtToken;
    private String refreshToken;
    private AccountStatus accountStatus;
    private Instant expiresAt;

}

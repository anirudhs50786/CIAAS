package com.motocart.ciaas_microservice.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 50, message = "User Name must be 5–50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 16, max = 128, message = "Password must be 16–128 characters")
    private String password;
}

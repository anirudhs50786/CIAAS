package com.motocart.ciaas_microservice.auth.validators;

import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;
import com.motocart.ciaas_microservice.auth.repository.UserRepository;
import com.motocart.ciaas_microservice.auth.service.PasswordPolicyService;
import org.springframework.stereotype.Service;

@Service
public class AuthValidationService {

    private final UserRepository userRepository;

    private final PasswordPolicyService passwordPolicyService;

    public AuthValidationService(UserRepository userRepository, PasswordPolicyService passwordPolicyService) {
        this.userRepository = userRepository;
        this.passwordPolicyService = passwordPolicyService;
    }

    public void validateSignUp(SignUpRequestDTO signUpRequestDTO) {
        String password = signUpRequestDTO.getPassword();
        String email = signUpRequestDTO.getEmail();
        String username = signUpRequestDTO.getUsername();

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use!");
        }
        if (userRepository.existsByUserName(username)) {
            throw new RuntimeException("Username is already taken!");
        }
        if (passwordPolicyService.isCommonPassword(password)) {
            throw new RuntimeException("Password is too weak or commonly used.");
        }
    }
}

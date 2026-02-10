package com.motocart.ciaas_microservice.auth.service;

import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;
import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.repository.RoleRepository;
import com.motocart.ciaas_microservice.auth.repository.UserRepository;
import com.motocart.ciaas_microservice.auth.validators.AuthValidationService;
import com.motocart.ciaas_microservice.types.AccountStatus;
import com.motocart.ciaas_microservice.types.Roles;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthValidationService authValidationService;

    public AuthenticationService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository, AuthValidationService authValidationService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authValidationService = authValidationService;
    }

    public UserEntity registerCustomerUser(SignUpRequestDTO signUpRequestDTO) {
        authValidationService.validateSignUp(signUpRequestDTO);
        String encodedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        RoleEntity userRoleEntity = roleRepository.findByAuthority(Roles.ROLE_USER.toString()).orElseThrow(() -> new EntityNotFoundException("No User role defined"));
        Set<RoleEntity> authorities = new HashSet<>();
        authorities.add(userRoleEntity);

        UserEntity user = UserEntity.builder()
                .userName(signUpRequestDTO.getUsername())
                .password(encodedPassword)
                .authorities(authorities)
                .createdOn(Instant.now())
                .accountStatus(AccountStatus.ACTIVE_INCOMPLETE.getStatusCode())
                .build();
        return userRepository.save(user);
    }

}

package com.motocart.ciaas_microservice.auth.service;

import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.repository.UserRepository;
import com.motocart.ciaas_microservice.profile.entity.UserProfileEntity;
import com.motocart.ciaas_microservice.profile.repository.UserProfileRepository;
import com.motocart.ciaas_microservice.util.MapperUtil;
import com.motocart.library.common.dto.UserDetailDTO;
import com.motocart.library.common.dto.UserSummaryDTO;
import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.library.common.types.Roles;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final AuthenticationService authenticationService;

    public AdminUserService(UserRepository userRepository,
                            UserProfileRepository userProfileRepository,
                            AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.authenticationService = authenticationService;
    }

    public UserEntity registerAdminUser(SignUpRequestDTO signUpRequestDTO) {
        return authenticationService.registerUser(signUpRequestDTO, Set.of(Roles.ROLE_USER, Roles.ROLE_ADMIN));
    }

    public List<UserSummaryDTO> getAllAdminUsers() {
        return MapperUtil.toUserSummaryList(userRepository.findByAuthoritiesRoleName(Roles.ROLE_ADMIN.name()));
    }

    public List<UserSummaryDTO> getAllCustomerUsers() {
        return MapperUtil.toUserSummaryList(userRepository.findByAuthoritiesRoleName(Roles.ROLE_USER.name()));
    }

    public UserDetailDTO getUserDetail(int userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        UserProfileEntity profile = userProfileRepository.findByUserId(userId).orElse(null);
        return MapperUtil.toUserDetailDTO(user, profile);
    }
}

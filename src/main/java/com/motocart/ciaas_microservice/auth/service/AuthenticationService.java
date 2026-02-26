package com.motocart.ciaas_microservice.auth.service;

import com.motocart.ciaas_microservice.auth.dto.request.SignInRequestDTO;
import com.motocart.ciaas_microservice.auth.dto.request.SignUpRequestDTO;
import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.repository.RoleRepository;
import com.motocart.ciaas_microservice.auth.repository.UserRepository;
import com.motocart.ciaas_microservice.auth.validators.AuthValidationService;
import com.motocart.ciaas_microservice.auth.vo.JwtVO;
import com.motocart.ciaas_microservice.types.AccountStatus;
import com.motocart.ciaas_microservice.types.Roles;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final AuthenticationManager authenticationManager;

    private final AuthValidationService authValidationService;

    private final JWTService jwtService;

    private final RefreshTokenService refreshTokenService;

    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 AuthValidationService authValidationService,
                                 JWTService jwtService, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.authValidationService = authValidationService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public UserEntity registerCustomerUser(SignUpRequestDTO signUpRequestDTO) {
        authValidationService.validateSignUp(signUpRequestDTO);
        String encodedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());
        RoleEntity userRoleEntity = roleRepository.findByAuthority(Roles.ROLE_USER.toString()).orElseThrow(() -> new EntityNotFoundException("No User role defined"));
        Set<RoleEntity> authorities = new HashSet<>();
        authorities.add(userRoleEntity);

        UserEntity user = UserEntity.builder()
                .username(signUpRequestDTO.getUsername())
                .email(signUpRequestDTO.getEmail())
                .password(encodedPassword)
                .authorities(authorities)
                .createdOn(Instant.now())
                .accountStatus(AccountStatus.ACTIVE_INCOMPLETE.getStatusCode())
                .build();
        return userRepository.save(user);
    }

    public JwtVO verifyCredentials(SignInRequestDTO signInRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getLoginId(), signInRequestDTO.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return createJwtVO(user.getUserId());
    }

    public JwtVO getNewAccessToken(String refreshToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();
        int userId = user.getUserId();
        refreshTokenService.validateRefreshToken(userId, refreshToken);
        JwtVO jwtVO = createJwtVO(userId);
        refreshTokenService.processTokenRefresh(jwtVO, refreshToken);
        return jwtVO;
    }

    /**
     * Creates a JwtVO instance with access token and refresh token
     *
     * @param userId - user id for whom the tokens are being generated
     * @return JwtVO instance
     */
    private JwtVO createJwtVO(int userId) {
        String accessToken = jwtService.generateAccessToken(String.valueOf(userId));
        return JwtVO.builder()
                .userId(userId)
                .accessToken(accessToken)
                .accessTokenExpiresIn(jwtService.getAccessTokenExpiration())
                .refreshToken(refreshTokenService.generateRefreshToken())
                .refreshTokenExpiresIn(refreshTokenService.getRefreshTokenExpiration())
                .build();
    }
}

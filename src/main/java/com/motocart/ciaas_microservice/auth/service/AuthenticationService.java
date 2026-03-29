package com.motocart.ciaas_microservice.auth.service;

import com.motocart.ciaas_microservice.auth.kafka.NotificationEventProducer;
import com.motocart.library.common.dto.request.SignInRequestDTO;
import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.repository.RoleRepository;
import com.motocart.ciaas_microservice.auth.repository.UserRepository;
import com.motocart.ciaas_microservice.auth.validators.AuthValidationService;
import com.motocart.ciaas_microservice.auth.vo.JwtVO;
import com.motocart.library.common.event.NotificationEvent;
import com.motocart.library.common.types.NotificationType;
import com.motocart.library.common.types.Roles;
import com.motocart.ciaas_microservice.util.AuthHelper;
import com.motocart.ciaas_microservice.util.MapperUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    private final NotificationEventProducer notificationEventProducer;

    public AuthenticationService(PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 AuthValidationService authValidationService,
                                 JWTService jwtService, RefreshTokenService refreshTokenService, NotificationEventProducer notificationEventProducer) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.authValidationService = authValidationService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.notificationEventProducer = notificationEventProducer;
    }

    public UserEntity registerCustomerUser(SignUpRequestDTO signUpRequestDTO) {
        return registerUser(signUpRequestDTO, Set.of(Roles.ROLE_USER));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserEntity registerAdminUser(SignUpRequestDTO signUpRequestDTO) {
        return registerUser(signUpRequestDTO, Set.of(Roles.ROLE_USER, Roles.ROLE_ADMIN));
    }

    private UserEntity registerUser(SignUpRequestDTO dto, Set<Roles> roles) {
        authValidationService.validateSignUp(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        Set<RoleEntity> authorities = roles.stream()
                .map(role -> roleRepository.findByAuthority(role.toString())
                .orElseThrow(() -> new EntityNotFoundException(role + " not defined")))
                .collect(Collectors.toSet());
        UserEntity userEntity = userRepository.save(MapperUtil.toUserEntity(dto, encodedPassword, authorities));
        sendUserRegNotification(userEntity);
        return userEntity;
    }

    public JwtVO verifyCredentials(SignInRequestDTO signInRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getLoginId(), signInRequestDTO.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String roles = MapperUtil.authoritiesToString(authentication.getAuthorities());
        return createJwtVO(user.getUserId(), roles);
    }

    /**
     * Creates a new access token using the refresh token stored in the database for the user
     *
     * @param refreshToken the refresh token sent by the client
     * @return the new access token
     */
    public JwtVO getNewAccessToken(String refreshToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId = AuthHelper.getAuthUserId();
        String roles = MapperUtil.authoritiesToString(authentication.getAuthorities());
        refreshTokenService.validateRefreshToken(userId, refreshToken);
        JwtVO jwtVO = createJwtVO(userId, roles);
        refreshTokenService.processTokenRefresh(jwtVO, refreshToken);
        return jwtVO;
    }

    /**
     * Creates a JwtVO instance with access token and refresh token
     *
     * @param userId - user id for whom the tokens are being generated
     * @param roles - roles of the user
     * @return JwtVO instance
     */
    private JwtVO createJwtVO(int userId, String roles) {
        String accessToken = jwtService.generateAccessToken(String.valueOf(userId), roles);
        return JwtVO.builder()
                .userId(userId)
                .accessToken(accessToken)
                .accessTokenExpiresIn(jwtService.getAccessTokenExpiration())
                .refreshToken(refreshTokenService.generateRefreshToken())
                .refreshTokenExpiresIn(refreshTokenService.getRefreshTokenExpiration())
                .build();
    }

    /**
     * Build the Kafka notification event and send it asynchronously
     * @param user the user entity
     */
    @Async("ciaasExecutor")
    private void sendUserRegNotification(UserEntity user) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("recipientName", user.getUsername());
        payload.put("loginLink", "dummy url");
        NotificationType type = user.getAuthorities().stream()
                .map(RoleEntity::getAuthority).
                anyMatch(s -> s.equals(Roles.ROLE_ADMIN.name())) ?
                NotificationType.ADMIN_REGISTRATION : NotificationType.USER_REGISTRATION;

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .notificationType(type)
                .recipientEmail(user.getEmail())
                .payload(payload)
                .build();
        notificationEventProducer.sendNotificationEvent(notificationEvent);
    }
}

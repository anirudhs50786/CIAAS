package com.motocart.ciaas_microservice.authz.service;

import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.auth.repository.UserRepository;
import com.motocart.ciaas_microservice.authz.entity.UserPermissionEntity;
import com.motocart.ciaas_microservice.authz.repository.PermissionRepository;
import com.motocart.ciaas_microservice.authz.repository.UserPermissionRepository;
import com.motocart.ciaas_microservice.types.PermissionEffect;
import com.motocart.ciaas_microservice.util.AuthHelper;
import com.motocart.library.cache.CacheNames;
import com.motocart.library.common.dto.EntitlementsDTO;
import com.motocart.library.common.exception.GlobalException;
import com.motocart.library.common.types.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntitlementService {

    private final UserPermissionRepository userPermissionRepository;

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    public EntitlementService(UserPermissionRepository userPermissionRepository, PermissionRepository permissionRepository, UserRepository userRepository) {
        this.userPermissionRepository = userPermissionRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    @Cacheable(value = CacheNames.USER_ENTITLEMENT_CACHE, key = "'user:' + #userId")
    public EntitlementsDTO getAllEntitlementsForLoggedInUser() {
        int userId = AuthHelper.getAuthUserId();
        Set<String> roles = AuthHelper.getRoles();
        return EntitlementsDTO.builder()
                .userId(userId)
                .roles(roles)
                .permissions(buildPermissions(userId, roles))
                .build();
    }

    @Cacheable(value = CacheNames.USER_ENTITLEMENT_CACHE, key = "'user:' + #userId")
    public EntitlementsDTO getAllEntitlements(int userId) {
        Set<String> roles = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException("User not found"))
                .getAuthorities()
                .stream()
                .map(RoleEntity::getAuthority)
                .collect(Collectors.toSet());

        return EntitlementsDTO.builder()
                .userId(userId)
                .roles(roles)
                .permissions(buildPermissions(userId, roles))
                .build();
    }

    private Set<String> buildPermissions(int userId, Set<String> roles) {

        // Get default permissions from roles
        Set<String> permissions = new HashSet<>(permissionRepository.findAllByRoles(roles));

        // Get override or custom permissions for the user
        Set<UserPermissionEntity> userPermissions = userPermissionRepository.findAllByUserId(userId);

        // Add or remove permissions based on the user permission effect
        for (var userPermission : userPermissions) {
            String permissionName = userPermission.getPermission().getPermissionName();
            if(permissionName == null) {
                continue;
            }
            if(userPermission.getEffect() == PermissionEffect.REVOKE) {
                permissions.remove(permissionName);
            } else {
                permissions.add(permissionName);
            }
        }
        return permissions;
    }

    public void canAccess(Permission forPermission) {
        Set<String> userPermissions = getAllEntitlementsForLoggedInUser().getPermissions();
        if(!userPermissions.contains(forPermission.name())) {
            log.warn("User is not authorized to access {}", forPermission);
            throw new AccessDeniedException("User is not authorized to access " + forPermission);
        }
    }
}

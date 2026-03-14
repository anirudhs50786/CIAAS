package com.motocart.ciaas_microservice.util;

import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.library.security.Principal;
import org.springframework.security.core.context.SecurityContextHolder;

public final class AuthHelper {

    private AuthHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static int getAuthUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Principal user) {
            return user.userId();
        }
        if (principal instanceof UserEntity user) {
            return user.getUserId();
        }
        throw new IllegalStateException("Principal is of unsupported type");
    }
}

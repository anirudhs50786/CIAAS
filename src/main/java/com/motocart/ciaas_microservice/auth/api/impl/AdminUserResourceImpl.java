package com.motocart.ciaas_microservice.auth.api.impl;

import com.motocart.ciaas_microservice.auth.api.AdminUserResource;
import com.motocart.ciaas_microservice.auth.service.AuthenticationService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;

@MotocartAPI("admin/users/")
public class AdminUserResourceImpl implements AdminUserResource {

    private final AuthenticationService authenticationService;

    public AdminUserResourceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @PostMapping("/register")
    public SignUpResponseDTO registerAdminUser(SignUpRequestDTO signUpRequestDTO) {
        return MapperUtil.toSignUpResponse(authenticationService.registerAdminUser(signUpRequestDTO));
    }

    @Override
    public void getAllAdminUsers() {

    }

    @Override
    public void getAllCustomerUsers() {

    }
}

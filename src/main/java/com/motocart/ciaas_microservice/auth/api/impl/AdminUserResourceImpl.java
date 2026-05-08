package com.motocart.ciaas_microservice.auth.api.impl;

import com.motocart.ciaas_microservice.auth.api.AdminUserResource;
import com.motocart.ciaas_microservice.auth.service.AdminUserService;
import com.motocart.ciaas_microservice.auth.service.AuthenticationService;
import com.motocart.ciaas_microservice.util.MapperUtil;
import com.motocart.library.common.annotation.MotocartAPI;
import com.motocart.library.common.dto.UserDetailDTO;
import com.motocart.library.common.dto.UserSummaryDTO;
import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;
import jakarta.ws.rs.QueryParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@MotocartAPI("admin/users/")
public class AdminUserResourceImpl implements AdminUserResource {

    private final AdminUserService adminUserService;

    public AdminUserResourceImpl(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Override
    @PostMapping("/register")
    public SignUpResponseDTO registerAdminUser(SignUpRequestDTO signUpRequestDTO) {
        return MapperUtil.toSignUpResponse(adminUserService.registerAdminUser(signUpRequestDTO));
    }

    @Override
    @GetMapping("/_query")
    public List<UserSummaryDTO> getAllAdminUsers() {
        return MapperUtil.toUserSummaryDTO(adminUserService.getAllAdminUsers());
    }

    @Override
    @GetMapping("/customer/_query")
    public List<UserSummaryDTO> getAllCustomerUsers() {
        return MapperUtil.toUserSummaryDTO(adminUserService.getAllCustomerUsers());
    }

    @Override
    @GetMapping("/_query/{userId}")
    public UserDetailDTO getUserDetailById(@PathVariable int userId) {
        return adminUserService.getUserDetail(userId);
    }
}

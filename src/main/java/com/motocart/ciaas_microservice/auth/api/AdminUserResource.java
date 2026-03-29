package com.motocart.ciaas_microservice.auth.api;

import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;

public interface AdminUserResource {

    SignUpResponseDTO registerAdminUser(SignUpRequestDTO signUpRequestDTO);

    void getAllAdminUsers();

    void getAllCustomerUsers();

}

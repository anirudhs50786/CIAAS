package com.motocart.ciaas_microservice.auth.api;

import com.motocart.library.common.dto.UserDetailDTO;
import com.motocart.library.common.dto.UserSummaryDTO;
import com.motocart.library.common.dto.request.SignUpRequestDTO;
import com.motocart.library.common.dto.response.SignUpResponseDTO;

import java.util.List;

public interface AdminUserResource {

    SignUpResponseDTO registerAdminUser(SignUpRequestDTO signUpRequestDTO);

    List<UserSummaryDTO> getAllAdminUsers();

    List<UserSummaryDTO> getAllCustomerUsers();

    UserDetailDTO getUserDetailById(int userId);

}

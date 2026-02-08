package com.motocart.ciaas_microservice.service;

import com.motocart.ciaas_microservice.dao.RoleDao;
import com.motocart.ciaas_microservice.dao.UserDao;
import com.motocart.ciaas_microservice.dto.RegistrationDTO;
import com.motocart.ciaas_microservice.entity.ApplicationUser;
import com.motocart.ciaas_microservice.entity.Role;
import com.motocart.ciaas_microservice.types.AccountStatus;
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

    private final UserDao userDao;

    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder, RoleDao roleDao, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
        this.userDao = userDao;
    }

    public ApplicationUser registerCustomerUser(RegistrationDTO registrationDTO) {
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        Role userRole = roleDao.findByAuthority("USER").orElseThrow(() -> new EntityNotFoundException("No User role defined"));
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        ApplicationUser user = ApplicationUser.builder()
                .userName(registrationDTO.getUsername())
                .password(encodedPassword)
                .authorities(authorities)
                .createdOn(Instant.now())
                .accountStatus(AccountStatus.ACTIVE_INCOMPLETE.getStatusCode())
                .build();
        return userDao.save(user);
    }
}

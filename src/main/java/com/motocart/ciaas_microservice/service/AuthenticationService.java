package com.motocart.ciaas_microservice.service;

import com.motocart.ciaas_microservice.dao.RoleDao;
import com.motocart.ciaas_microservice.dao.UserDao;
import com.motocart.ciaas_microservice.entity.ApplicationUser;
import com.motocart.ciaas_microservice.entity.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApplicationUser registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleDao.findByAuthority("USER").orElseThrow(() -> new EntityNotFoundException("No User role defined"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);
        return userDao.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }
}

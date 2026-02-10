package com.motocart.ciaas_microservice.auth.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class PasswordPolicyService {

    private final Set<String> commonPasswords = new HashSet<>();

    @PostConstruct
    public void init() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/security/common-passwords.txt"))))) {
            br.lines()
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .filter(s -> !s.isEmpty())
                    .forEach(commonPasswords::add);
        } catch (NullPointerException e) {
            log.error("NPE: Null error when loading common passwords.", e);
        } catch (IOException e) {
            log.error("IOE: Error loading common passwords, with error {}", e.getMessage());
        }
        log.info("Loaded {} common passwords", commonPasswords.size());
    }

    public boolean isCommonPassword(String password) {
        return commonPasswords.contains(password.toLowerCase());
    }
}

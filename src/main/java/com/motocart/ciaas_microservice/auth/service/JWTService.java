package com.motocart.ciaas_microservice.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secret.private}")
    private String privateKeyString;

    @Value("${jwt.secret.public}")
    private String publicKeyString;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Value("${jwt.expiration.access-token}")
    @Getter
    private int accessTokenExpiration;

    @PostConstruct
    private void init() {
        // load the RSA keys
        try {
           byte [] privateKeyBytes = Decoders.BASE64.decode(privateKeyString);
            KeySpec privateKeySpec
                    = new PKCS8EncodedKeySpec(privateKeyBytes);
            this.privateKey = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec);

            byte [] publicKeyBytes = Decoders.BASE64.decode(publicKeyString);
            KeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            this.publicKey = KeyFactory.getInstance("RSA").generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RSA keys", e);
        }
    }

    public String generateAccessToken(String subject, String roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("username", "dummy");
        return Jwts.builder()
                .claims()
                .add(claims)
                .and()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userId) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("username", String.class));
    }
}

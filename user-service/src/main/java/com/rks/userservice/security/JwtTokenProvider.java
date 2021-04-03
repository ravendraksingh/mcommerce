package com.rks.userservice.security;

import com.rks.userservice.domain.User;
import com.rks.userservice.domain.UserPrincipal;
import com.rks.userservice.exception.DefaultJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public JwtTokenProvider() {
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        logger.debug("User email is: {}", userPrincipal.getEmail());
        jwtExpirationInMs = 10* 60 * 1000;
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setId("authJWT")
                .setSubject(userPrincipal.getEmail())
                .claim("authorities", StringUtils.collectionToCommaDelimitedString(userPrincipal.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey("JWTSuperSecretKey").parseClaimsJws(token).getBody();
            User user = new User();
            user.setUsername(body.getSubject());
            return user;
        } catch (DefaultJwtException e) {
            logger.debug("Exception occurred. Message is: {}", e.getMessage());
            return null;
        }
    }
}

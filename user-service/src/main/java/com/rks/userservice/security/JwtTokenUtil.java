package com.rks.userservice.security;

import com.rks.userservice.exception.DefaultJwtException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.rks.userservice.constants.UserServiceConfigConstants.JWT_SECRET_KEY;
import static com.rks.userservice.constants.UserServiceConstants.FAILURE;
import static com.rks.userservice.constants.UserServiceErrorCodes.INVALID_TOKEN_ERR_CODE;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.*;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -4479509513976748900L;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

//    // retrieve authorities from token
//    public String getAuthoritiesFromToken(String token) {
//        return getClaimFromToken(token, );
//    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY).compact();
    }

    //validate token
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public Boolean isNonBlankAndNotExpiredToken(String token) {
        final String username = getUsernameFromToken(token);
        return (StringUtils.hasText(username) && !isTokenExpired(token));
    }

    public boolean validateToke(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                UnsupportedJwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token");
            log.trace("Invalid JWT token trace: {}", getCustomErrorMessage(e));
            //throw new DefaultJwtException(e.getMessage(), FAILURE, INVALID_TOKEN_ERR_CODE, INVALID_TOKEN_ERR_MSG);
            throw new DefaultJwtException(e.getMessage(), e, FAILURE, INVALID_TOKEN_ERR_CODE, getCustomErrorMessage(e));
        }
    }

    private String getCustomErrorMessage(RuntimeException e) {
        String message = INVALID_TOKEN_ERR_MSG;
        if (e instanceof SignatureException ) {
            message = INVALID_TOKEN_SIGNATURE_ERR_MSG;
        } else if (e instanceof  MalformedJwtException) {
            message = MALFORMED_TOKEN_ERR_MSG;
        } else if (e instanceof ExpiredJwtException) {
            message = TOKEN_EXPIRED_ERR_MSG;
        } else if ( e instanceof UnsupportedJwtException) {
            message = UNSUPPORTED_TOKEN_ERR_MSG;
        }
        return message;
    }
}

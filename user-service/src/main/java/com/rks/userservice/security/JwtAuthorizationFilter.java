package com.rks.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rks.userservice.domain.User;
import com.rks.userservice.dto.response.ApiError;
import com.rks.userservice.exception.BaseException;
import com.rks.userservice.exception.DefaultJwtException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.rks.userservice.constants.UserServiceConstants.FAILURE;
import static com.rks.userservice.constants.UserServiceErrorCodes.INVALID_TOKEN_ERR_CODE;
import static com.rks.userservice.constants.UserServiceErrorCodes.MISSING_TOKEN_ERR_CODE;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.INVALID_TOKEN_ERR_MSG;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.MISSING_TOKEN_ERR_MSG;


@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;

    private ObjectMapper mapper = new ObjectMapper();
    private JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public JwtAuthorizationFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //super.doFilterInternal(request, response, chain);
        // log.debug("############### JwtAuthorizationFilter : initializing token validation ################");
       try {
            log.debug("Fetching token from request");

            if (!isTokenAvailableAndValid(request)) {
                throw new DefaultJwtException(INVALID_TOKEN_ERR_MSG, FAILURE, INVALID_TOKEN_ERR_CODE, INVALID_TOKEN_ERR_MSG);
            }
            else {
                String token = fetchTokenFromRequest(request);
                String username = jwtTokenUtil.getUsernameFromToken(token);
                //String authorities = jwtTokenUtil.getClaimFromToken();
                if (!StringUtils.hasText(username)) {
                    throw new DefaultJwtException(INVALID_TOKEN_ERR_MSG, FAILURE, INVALID_TOKEN_ERR_CODE, INVALID_TOKEN_ERR_MSG);
                } else {
                    User user = new User();
                    user.setUsername(username);
                    setupSpringAuthentication(user);
                }
            }
            chain.doFilter(request, response);
       } catch (BaseException ex) {
           log.error("JWT authorization error. Message: {}", ex.getMessage());
           ApiError apiError = new ApiError(FAILURE, ex.getCustomMessage());
           response.setContentType("application/json");
           response.setStatus(HttpStatus.UNAUTHORIZED.value());
           response.getOutputStream().println(mapper.writeValueAsString(apiError));
       }
//       } catch (Exception ex) {
//            log.error("JWT authorization error. Message: {}", ex.getMessage());
//            ApiError apiError = new ApiError(FAILURE, ex.getMessage());
//            response.setContentType("application/json");
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.getOutputStream().println(mapper.writeValueAsString(apiError));
//       }

    }

    private void setupSpringAuthentication(User user) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(),
                null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    // Authentication method in Spring flow
    private void setupSpringAuthentication(Claims claims) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(),
                null, null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String fetchTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }

    private boolean isTokenAvailableAndValid(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header==null || !header.startsWith("Bearer ")) {
            throw new DefaultJwtException(MISSING_TOKEN_ERR_MSG, FAILURE, MISSING_TOKEN_ERR_CODE, MISSING_TOKEN_ERR_MSG);
        }
        return jwtTokenUtil.validateToke(header.substring(7));
    }
}

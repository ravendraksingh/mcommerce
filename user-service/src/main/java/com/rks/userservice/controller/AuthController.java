package com.rks.userservice.controller;

import com.rks.mcommon.api.response.ApiError;
import com.rks.mcommon.api.response.ApiSuccess;
import com.rks.userservice.domain.*;
import com.rks.userservice.dto.ApiSuccessResponse;
import com.rks.userservice.exception.AppException;
import com.rks.userservice.repository.RoleRepository;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.rks.userservice.constants.ErrorCodeConstants.EMAIL_ALREADY_IN_USE_ERR_CODE;
import static com.rks.userservice.constants.ErrorCodeConstants.USERNAME_ALREADY_IN_USE_ERR_CODE;
import static com.rks.userservice.constants.ErrorMessageConstants.EMAIL_ALREADY_IN_USE_ERR_MSG;
import static com.rks.userservice.constants.ErrorMessageConstants.USERNAME_ALREADY_IN_USE_ERR_MSG;

//@ConditionalOnProperty(value = "app.security.enabled",
//        havingValue = "true",
//        matchIfMissing = false)
@Slf4j
@Profile("local-security")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        Map<String, Object> data = new HashMap<>();
        data.put("tokenType", "Bearer");
        data.put("accessToken", jwt);
        ApiSuccessResponse apiSuccessResponse = new ApiSuccessResponse("success", "Login Success", data);
        return ResponseEntity.ok(apiSuccessResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (errors.hasErrors()) {
//            log.debug("Error occurred in registering user. Errors: {}", errors);
//            return new ResponseEntity<ErrorResponse>(
//                    new ErrorResponse(Arrays.asList(new ErrorObject(45, "new error message"))),
//                    HttpStatus.BAD_REQUEST);
//        }
        Boolean emailTaken = userRepository.existsByEmail(signUpRequest.getEmail());
        Boolean userNameTaken = userRepository.existsByUsername(signUpRequest.getUsername());

        if(emailTaken) {
            return new ResponseEntity(new ApiError(EMAIL_ALREADY_IN_USE_ERR_CODE, EMAIL_ALREADY_IN_USE_ERR_MSG),
                    HttpStatus.BAD_REQUEST);
        }

        if(userNameTaken) {
            return new ResponseEntity(new ApiError(USERNAME_ALREADY_IN_USE_ERR_CODE, USERNAME_ALREADY_IN_USE_ERR_MSG),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
        System.out.println(userRole);
        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiSuccess("User registered successfully"));
    }
}

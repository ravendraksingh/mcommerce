package com.rks.userservice.controller;

import com.rks.userservice.domain.*;
import com.rks.userservice.dto.request.SignUpRequest;
import com.rks.userservice.dto.response.ApiSuccessResponse;
import com.rks.userservice.dto.request.LoginRequest;
import com.rks.userservice.dto.response.JwtResponse;
import com.rks.userservice.exception.BaseException;
import com.rks.userservice.exception.RequestConflictException;
import com.rks.userservice.repository.RoleRepository;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.rks.userservice.constants.UserServiceConstants.*;
import static com.rks.userservice.constants.UserServiceErrorCodes.*;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.*;

//@ConditionalOnProperty(value = "app.security.enabled",
//        havingValue = "true",
//        matchIfMissing = false)
@Slf4j
@Profile("local-security")
@RestController
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

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
//        log.debug("Checking if email {} exists", request.getUsernameOrEmail());
//        boolean userFound = userRepository.existsByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());
//        if(!userFound) {
//            throw new NotFoundException(FAILED,USER_NOT_FOUND_ERR_CODE,USER_NOT_FOUND_ERR_MSG);
//        }
        log.info("Going to authenticate user with userName/Email: {}.", request.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        log.info("Authentication successful.");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
//        Set<String> roleSet = Collections.singleton(userDetails.getAuthorities().toString());
//        String roles = StringUtils.collectionToCommaDelimitedString(roleSet);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        log.info("Roles: {}", roles);
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (errors.hasErrors()) {
//            log.debug("Error occurred in registering user. Errors: {}", errors);
//            return new ResponseEntity<ErrorResponse>(
//                    new ErrorResponse(Arrays.asList(new ErrorObject(45, "new error message"))),
//                    HttpStatus.BAD_REQUEST);
//        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RequestConflictException(FAILED, DUPLICATE_EMAIL_ERR_CODE,DUPLICATE_EMAILID_ERR_MSG);
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RequestConflictException(FAILED,DUPLICATE_USERNAME_ERR_CODE,DUPLICATE_USERNAME_ERR_MSG);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()));
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() ->
                new BaseException(FAILED, 9301, "User Role not set"));
        user.setRoles(Collections.singleton(userRole));

        //setting admin role for testing purposes
        Set<Role> fetchedRoles = user.getRoles();
//        fetchedRoles.add(new Role(RoleName.ROLE_ADMIN));

        User result = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiSuccessResponse(SUCCESS, "User registered successfully"));
    }
}

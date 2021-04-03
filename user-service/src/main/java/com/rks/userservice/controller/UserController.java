package com.rks.userservice.controller;

import com.rks.userservice.domain.User;
import com.rks.userservice.exception.NotFoundException;
import com.rks.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sun.net.www.protocol.http.AuthenticationHeader;

import java.security.Principal;
import java.util.Optional;

import static com.rks.userservice.constants.UserServiceConstants.FAILURE;
import static com.rks.userservice.constants.UserServiceErrorCodes.USER_NOT_FOUND_ERR_CODE;
import static com.rks.userservice.constants.UserServiceErrorMessageConstants.USER_NOT_FOUND_ERR_MSG;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{email}")
    public ResponseEntity deleteByEmail(@PathVariable("email") String email, Principal principal) {
        Principal fetchedPrincipal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug("FetchedPrincipal: {}", fetchedPrincipal);
        //userService.deleteUserByEmail(email);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/users")
    public ResponseEntity getAllUsersPublic() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/api/secure/users/{email}")
    public ResponseEntity getUserSecure(@PathVariable("email") String email) {
        Optional<User> optionalUser = userService.getSecureUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(FAILURE, USER_NOT_FOUND_ERR_CODE, USER_NOT_FOUND_ERR_MSG);
        }
        User user = optionalUser.get();
        return ResponseEntity.ok(user);
    }
}

package com.rks.userservice.service;

import com.rks.userservice.domain.User;
import com.rks.userservice.domain.User360DegreeView;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User360DegreeView get360DegreeView(String userName);
    void deleteUser(Long userId);
    void deleteUserByEmail(String email);
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);

    @PreAuthorize(value = "authentication.principal.equals(#email)")
    Optional<User> getSecureUserByEmail(String email);
}

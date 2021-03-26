package com.rks.userservice.service;

import com.rks.userservice.domain.User360DegreeView;

public interface UserService {
    User360DegreeView get360DegreeView(String userName);
    void deleteUser(Long userId);
}

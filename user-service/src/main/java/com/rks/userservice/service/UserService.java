package com.rks.userservice.service;

import com.rks.userservice.model.User360DegreeView;

public interface UserService {
    User360DegreeView get360DegreeView(String userName);
}

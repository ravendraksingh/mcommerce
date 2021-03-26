package com.rks.userservice.controller;

import com.rks.userservice.domain.User360DegreeView;
import com.rks.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/user-service/ext")
public class UserControllerExternal {

    private final UserService userService;

    public UserControllerExternal(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/users/{userName}/360degreeview")
    public User360DegreeView getUser360DegreeView(@Valid @PathVariable final String userName) {
        return userService.get360DegreeView(userName);
    }
}

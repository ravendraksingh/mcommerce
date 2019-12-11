package com.rks.userservice.controller;

import com.rks.userservice.entities.User;
import com.rks.userservice.model.User360DegreeView;
import com.rks.userservice.repository.UserRepository;
import com.rks.userservice.service.UserAddressService;
import com.rks.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController("/user-service/ext")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/v1/users/{userName}/360degreeview")
    public User360DegreeView getUser360DegreeView(@Valid @PathVariable final String userName) {
        return userService.get360DegreeView(userName);
    }
}

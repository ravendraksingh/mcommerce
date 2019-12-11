package com.rks.userservice.controller;

import com.rks.userservice.entities.UserAddress;
import com.rks.userservice.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user-service/ext")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/v1/address")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAddress add(@Valid @RequestBody final UserAddress userAddress) {
        return userAddressService.create(userAddress);
    }
}

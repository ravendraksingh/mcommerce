package com.rks.userservice.controller;

import com.rks.userservice.dto.ApiSuccessResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/secure")
public class SecureApiController {

    //@PreAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public ApiSuccessResponse justSayHello() {
        return new ApiSuccessResponse("success", "Hello World!");
    }
}

package com.rks.userservice.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class JwtResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public JwtResponse(String accessToken, String username, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

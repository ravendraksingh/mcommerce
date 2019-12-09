package com.rks.userservice.security;

public interface SecurityParams {
    public static final String JWT_HEADER_NAME = "Authorization";
    public static final String SECRET = "ravendraksingh@gmail.com";
    public static final long EXPIRATION = 60000*60;
    public static final String HEADER_PREFIX = "Bearer ";
}

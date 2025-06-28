package com.tshewang.todo.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public class JwtServiceImpl implements JwtService{
    private String SECRET_KEY;
    private long JWTEXPIRATION;

    @Override
    public String extractUsername(String token) {

        return "";
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return "";
    }
}

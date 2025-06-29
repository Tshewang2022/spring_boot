package com.tshewang.todo.service;

import com.tshewang.todo.request.RegisterRequest;

public interface AuthenticationService {
    void register(RegisterRequest input ) throws Exception;
}

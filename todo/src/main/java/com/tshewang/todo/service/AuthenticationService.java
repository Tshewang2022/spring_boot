package com.tshewang.todo.service;

import com.tshewang.todo.request.AuthenticationRequest;
import com.tshewang.todo.request.RegisterRequest;
import com.tshewang.todo.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest input ) throws Exception;

    AuthenticationResponse login(AuthenticationRequest request );
}

package com.tshewang.todo.service;

import com.tshewang.todo.response.UserResponse;

public interface UserService {
    UserResponse getUserInfo ();
    void deleteUser();
}

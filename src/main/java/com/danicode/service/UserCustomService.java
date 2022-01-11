package com.danicode.service;
import com.danicode.dto.UserRequest;
import com.danicode.model.User;

public interface UserCustomService {
    User userRegister(UserRequest userRequest);

}

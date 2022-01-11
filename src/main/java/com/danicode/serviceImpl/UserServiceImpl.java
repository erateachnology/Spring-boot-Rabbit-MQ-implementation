package com.danicode.serviceImpl;

import com.danicode.dto.UserRequest;
import com.danicode.model.User;
import com.danicode.repository.UserRepository;
import com.danicode.service.UserCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserCustomService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User userRegister(UserRequest userRequest) {
        //Get the image from the classpath , resource folder
        // Insert it into your jasper report.
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setConfirmPassword(passwordEncoder.encode(userRequest.getConfirmPassword()));
        user.setRoles("USER");
       return userRepository.save(user);
    }
}

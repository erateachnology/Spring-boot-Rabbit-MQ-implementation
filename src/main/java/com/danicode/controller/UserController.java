package com.danicode.controller;

import com.danicode.configuration.JwtUtility;
import com.danicode.dto.LoginRequest;
import com.danicode.dto.Message;
import com.danicode.dto.Response;
import com.danicode.dto.UserRequest;
import com.danicode.model.User;
import com.danicode.service.PublishService;
import com.danicode.service.UserCustomService;
import com.danicode.serviceImpl.CustomUserDetails;
import com.danicode.serviceImpl.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully";
    public static final String USER_LOGIN_SUCCESSFUL = "User login successful";
    @Autowired
    private UserCustomService userCustomService;

    @Autowired
    private PublishService publishMessage;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/registration")
    public ResponseEntity<Response> UserRegister(@Valid @RequestBody UserRequest userRequest){
        Response response = new Response();
        try{
           User user =  userCustomService.userRegister(userRequest);
            response.setMessage(USER_REGISTERED_SUCCESSFULLY);
            response.setUser(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = new Response();
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            final UserDetails userDetails = userService.loadUserByUsername(loginRequest.getEmail());
            if(userDetails != null){
                final String token =
                        jwtUtility.generateToken(userDetails);
                response.setUserName(((CustomUserDetails) userDetails).getUser().getFirstName());
                response.setEmail(((CustomUserDetails) userDetails).getUser().getEmail());
                response.setToken(token);

            }

            response.setMessage(USER_LOGIN_SUCCESSFUL);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (BadCredentialsException e){
            response.setMessage(e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<Response> publishMessage(@RequestBody Message message){
        Response response = new Response();
        try{
            publishMessage.publishMessage(message);
            response.setMessage("Message published successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception e){
           response.setMessage(e.getMessage());
           return new ResponseEntity<>(response,HttpStatus.NO_CONTENT);
        }
    }

}

package com.danicode.dto;

import com.danicode.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private String message;
    private User user;
    private String userName;
    private  String email;
    private String token;
    private Message subMessage;
}

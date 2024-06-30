package com.eventhive.eventHive.Auth.dto;

import lombok.Data;

@Data
public class LoginReqDto {
    private String email;
    private String password;
}

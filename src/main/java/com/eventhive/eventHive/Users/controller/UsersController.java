package com.eventhive.eventHive.Users.controller;

import com.eventhive.eventHive.Response.Response;
import com.eventhive.eventHive.Users.Service.UsersService;
import com.eventhive.eventHive.Users.dto.RegisterReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDto reqDto){
        return Response.successResponse("User successfully registered", usersService.register(reqDto));
    }
}

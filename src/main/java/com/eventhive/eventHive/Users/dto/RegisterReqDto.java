package com.eventhive.eventHive.Users.dto;

import com.eventhive.eventHive.Users.Entity.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterReqDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotBlank(message = "You need to put the password")
    private String password;

    @NotBlank(message = "You need to put the same password")
    private String passwordMatch;

    public Users toEntity(){
        Users user = new Users();
        user.setEmail(email);
        user.setUsername(name);
        user.setPassword(password);
        return user;
    }

}

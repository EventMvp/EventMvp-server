package com.eventhive.eventHive.Users.dto;

import com.eventhive.eventHive.Users.Entity.Users;
import lombok.Data;

@Data
public class RegisterRespDto {
    private String username;
    private String email;

    public static RegisterRespDto fromEntity (Users user){
        RegisterRespDto respDto = new RegisterRespDto();
        respDto.setEmail(user.getEmail());
        respDto.setUsername(user.getUsername());
        return respDto;
    }
}

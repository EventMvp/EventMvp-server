package com.eventhive.eventHive.Users.Service;

import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.dto.RegisterReqDto;
import com.eventhive.eventHive.Users.dto.RegisterRespDto;
import com.eventhive.eventHive.Users.dto.UserProfileDto;

public interface UsersService {
    Users getUserById(Long userId);
    RegisterRespDto register(RegisterReqDto user);
    UserProfileDto getProfile();
    Users findById(Long userId);
}

package com.eventhive.eventHive.Users.dto;

import com.eventhive.eventHive.Users.Entity.Users;
import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;
    private String username;
    private String avatarUrl;
    private String email;
    private Integer points;
    private String referralCode;
    private Boolean isReferral;

    public static UserProfileDto fromEntity(Users users){
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setAvatarUrl(users.getProfilePicture());
        userProfileDto.setEmail(users.getEmail());
        userProfileDto.setUsername(users.getUsername());
        userProfileDto.setReferralCode(users.getReferralCode());
        userProfileDto.setIsReferral(users.getIsUsedReferralBenefit());
        return userProfileDto;
    }
}

package com.eventhive.eventHive.Referral.Service;

import com.eventhive.eventHive.Users.Entity.Users;

public interface ReferralService {
    void createReferral (Users referringUser, Users savedUsers);
}

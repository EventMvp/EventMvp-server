package com.eventhive.eventHive.Referral.Service.Impl;

import com.eventhive.eventHive.Referral.Entity.Referral;
import com.eventhive.eventHive.Referral.Repository.ReferralRepository;
import com.eventhive.eventHive.Referral.Service.ReferralService;
import com.eventhive.eventHive.Users.Entity.Users;
import org.springframework.stereotype.Service;

@Service
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository repository;

    public ReferralServiceImpl(ReferralRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createReferral(Users referringUser, Users savedUsers) {
        Referral referral = new Referral();
        referral.setUser(referringUser);
        referral.setReferredUser(savedUsers);
        repository.save(referral);
    }
}

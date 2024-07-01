package com.eventhive.eventHive.Referral.Repository;

import com.eventhive.eventHive.Referral.Entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferralRepository extends JpaRepository<Referral, Long> {
}

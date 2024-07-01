package com.eventhive.eventHive.Voucher.Repository;

import com.eventhive.eventHive.Voucher.Entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
}

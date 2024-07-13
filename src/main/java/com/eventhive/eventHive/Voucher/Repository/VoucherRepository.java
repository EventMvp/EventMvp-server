package com.eventhive.eventHive.Voucher.Repository;

import com.eventhive.eventHive.Voucher.Entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Optional<List<Voucher>> findByEventId(Long eventId);
}

package com.eventhive.eventHive.Voucher.Service;

import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.Voucher.dto.VoucherDto;

import java.math.BigDecimal;
import java.util.List;

public interface VoucherService {
    Voucher validateVoucher(Long voucherId);
    void createVoucher(Voucher voucher);
    void issueReferralVoucher(Events events);
    BigDecimal applyVoucher(BigDecimal totalAmount, Long voucherId);
    Voucher findById(Long voucherId);

    List<VoucherDto> findVoucherByEventID(Long eventId);
}

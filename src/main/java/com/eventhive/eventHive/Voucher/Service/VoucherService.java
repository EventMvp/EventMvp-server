package com.eventhive.eventHive.Voucher.Service;

import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Voucher.Entity.Voucher;

import java.math.BigDecimal;

public interface VoucherService {
    Voucher validateVoucher(Long voucherId);
    void createVoucher(Voucher voucher);
    void issueReferralVoucher(Users users);
    BigDecimal applyVoucher(BigDecimal totalAmount, Long voucherId);

    Voucher findById(Long voucherId);
}

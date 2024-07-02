package com.eventhive.eventHive.Voucher.Service;

import com.eventhive.eventHive.Voucher.Entity.Voucher;

public interface VoucherService {
    Voucher validateVoucher(Long voucherId);

    void createVoucher(Voucher voucher);
}

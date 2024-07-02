package com.eventhive.eventHive.Voucher.Service.Impl;

import com.eventhive.eventHive.Exceptions.VoucherNotExistException;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.Voucher.Repository.VoucherRepository;
import com.eventhive.eventHive.Voucher.Service.VoucherService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Voucher validateVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(()->new VoucherNotExistException("Voucher is not exist"));
        if (voucher.getExpiryDate().isBefore(LocalDate.now())){
            throw new VoucherNotExistException("Voucher is already expire");
        }
        return voucher;
    }

    @Override
    public void createVoucher(Voucher voucherRequest) {
        voucherRepository.save(voucherRequest);
    }
}

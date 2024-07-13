package com.eventhive.eventHive.Voucher.Service.Impl;

import com.eventhive.eventHive.Exceptions.VoucherNotExistException;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.Voucher.Repository.VoucherRepository;
import com.eventhive.eventHive.Voucher.Service.VoucherService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    @Override
    public void issueReferralVoucher(Users users) {
        Voucher voucher = new Voucher();
        voucher.setName("Referral 10% Discount");
        voucher.setDiscountPercentage(10);
        voucher.setExpiryDate(LocalDate.now().plusMonths(3));
        voucher.setReferralBased(true);
        voucher.setOrganizer(users);

        voucherRepository.save(voucher);
    }

    @Override
    public BigDecimal applyVoucher(BigDecimal totalAmount, Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotExistException("Voucher is not exist"));

        if (voucher.getExpiryDate().isBefore(LocalDate.now())){
            throw new VoucherNotExistException("Voucher has expired");
        }

        return totalAmount.multiply(BigDecimal.valueOf(voucher.getDiscountPercentage()))
                .divide(BigDecimal.valueOf(100), RoundingMode.FLOOR);
    }

    @Override
    public Voucher findById(Long voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new VoucherNotExistException("Voucher is not exist"));
    }
}

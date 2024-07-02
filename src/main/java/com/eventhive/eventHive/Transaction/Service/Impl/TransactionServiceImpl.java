package com.eventhive.eventHive.Transaction.Service.Impl;

import com.eventhive.eventHive.Exceptions.VoucherNotExistException;
import com.eventhive.eventHive.Transaction.Entity.Transaction;
import com.eventhive.eventHive.Transaction.Repository.TransactionRepository;
import com.eventhive.eventHive.Transaction.Service.TransactionService;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import com.eventhive.eventHive.TransactionItem.Repository.TransactionItemRepository;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.Voucher.Repository.VoucherRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final UsersRepository usersRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final TransactionRepository transactionRepository;
    private final VoucherRepository voucherRepository;

    public TransactionServiceImpl(UsersRepository usersRepository, TransactionItemRepository transactionItemRepository, TransactionRepository transactionRepository, VoucherRepository voucherRepository) {
        this.usersRepository = usersRepository;
        this.transactionItemRepository = transactionItemRepository;
        this.transactionRepository = transactionRepository;
        this.voucherRepository = voucherRepository;
    }

    @Override
    public Transaction createTransaction(Long userId, List<TransactionItem> items, Long voucherId) {
        Transaction trx = new Transaction();
        trx.setUser(usersRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User is not found")));

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (TransactionItem item : items){
            if (item.getTotalPrice() == null){
                throw new RuntimeException("Total price of transaction is cannot be null");
            }
            totalAmount = totalAmount.add(BigDecimal.valueOf(item.getTotalPrice()));
        }

        if (voucherId != null){
            Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(()-> new VoucherNotExistException("Voucher not exist"));
            if (voucher.getDiscountPercentage() > 0){
                BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(voucher.getDiscountPercentage()).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
                totalAmount = totalAmount.subtract(discount);
                trx.setVoucher(voucher);
            }
        }

        trx.setTotalAmount(totalAmount);
        Transaction savedTrx = transactionRepository.save(trx);

        for (TransactionItem item : items){
            item.setTransaction(savedTrx);
            transactionItemRepository.save(item);
        }
        return savedTrx;
    }
}

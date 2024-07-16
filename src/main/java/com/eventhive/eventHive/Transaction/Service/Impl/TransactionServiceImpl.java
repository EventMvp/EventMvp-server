package com.eventhive.eventHive.Transaction.Service.Impl;

import com.eventhive.eventHive.Auth.helper.Claims;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.EventTicket.Repository.EventTicketRepository;
import com.eventhive.eventHive.EventTicket.Service.EventTicketService;
import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Events.Repository.EventsRepository;
import com.eventhive.eventHive.Events.Service.EventsService;
import com.eventhive.eventHive.Exceptions.EventNotExistException;
import com.eventhive.eventHive.Exceptions.EventTicketNotExistException;
import com.eventhive.eventHive.Exceptions.VoucherNotExistException;
import com.eventhive.eventHive.PointHistory.Service.PointHistoryService;
import com.eventhive.eventHive.Transaction.Dto.TransactionRequestDto;
import com.eventhive.eventHive.Transaction.Dto.TransactionResponseDto;
import com.eventhive.eventHive.Transaction.Entity.Transaction;
import com.eventhive.eventHive.Transaction.Repository.TransactionRepository;
import com.eventhive.eventHive.Transaction.Service.TransactionService;
import com.eventhive.eventHive.TransactionItem.Dto.TransactionItemDto;
import com.eventhive.eventHive.TransactionItem.Dto.TransactionItemRequestDto;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import com.eventhive.eventHive.TransactionItem.Repository.TransactionItemRepository;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import com.eventhive.eventHive.Users.Service.UsersService;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import com.eventhive.eventHive.Voucher.Repository.VoucherRepository;
import com.eventhive.eventHive.Voucher.Service.VoucherService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final UsersService usersService;
    private final EventsService eventsService;
    private final TransactionRepository transactionRepository;
    private final EventTicketService eventTicketService;
    private final VoucherService voucherService;
    private final PointHistoryService pointHistoryService;

    public TransactionServiceImpl(UsersService usersService, EventsService eventsService, TransactionRepository transactionRepository, EventTicketService eventTicketService, VoucherService voucherService, PointHistoryService pointHistoryService) {
        this.usersService = usersService;
        this.eventsService = eventsService;
        this.transactionRepository = transactionRepository;
        this.eventTicketService = eventTicketService;
        this.voucherService = voucherService;
        this.pointHistoryService = pointHistoryService;
    }

    @Transactional
    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {
        Users user = usersService.findById(requestDto.getUserId());

        Events event = eventsService.findById(requestDto.getEventId());

        // 2. Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<TransactionItem> transactionItems = new ArrayList<>();

        for (TransactionItemRequestDto item : requestDto.getItems()){
            EventTicket eventTicket = eventTicketService.getEventTicketById(item.getEventTicketId());

            if (!Objects.equals(eventTicket.getEvent().getId(), item.getEventId())){
                throw new RuntimeException("Event Ticket does not belong to this event");
            }

            BigDecimal subtotal = eventTicket.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setEventTicket(eventTicket);
            transactionItem.setQuantity(item.getQuantity());
            transactionItem.setPrice(eventTicket.getPrice());
            transactionItem.setTotalPrice(subtotal);
            transactionItem.setEvent(eventTicket.getEvent());
            transactionItems.add(transactionItem);

            //reduce ticket seat
            eventTicketService.reduceTicket(eventTicket, item.getQuantity());
        }
        // 3. Apply voucher
        Voucher voucher = voucherService.findById(requestDto.getVoucherId());
        if (requestDto.getVoucherId() != null){
            BigDecimal discountAmount = voucherService.applyVoucher(totalAmount, requestDto.getVoucherId());
        }
        // 4. Redeem points
        int pointUsed = 0;
        if (requestDto.isUsePoints()){
            pointUsed = pointHistoryService.redeemPoints(requestDto.getUserId(), totalAmount);
            totalAmount = totalAmount.subtract(BigDecimal.valueOf(pointUsed));
        }
        // 5. Create transaction
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setEvents(event);
        transaction.setVoucher(voucher);
        transaction.setTotalAmount(totalAmount);
        for (TransactionItem item : transactionItems){
            item.setTransaction(transaction);
        }
        transaction.setTransactionItems(transactionItems);
        transactionRepository.save(transaction);

        // create the response
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setTransactionId(transaction.getId());
        responseDto.setTotalAmount(transaction.getTotalAmount());
        responseDto.setPointUsed(pointUsed);

        List<TransactionItemDto> itemDtos = transactionItems.stream()
                .map(TransactionItemDto::convertToDto)
                .toList();
        responseDto.setTransactionItems(itemDtos);

        return responseDto;

    }


//    @Override
//    public Transaction createTransaction(Long userId, List<TransactionItem> items, Long voucherId) {
//        Transaction trx = new Transaction();
//        trx.setUser(usersRepository.findById(userId).orElseThrow(()->new UsernameNotFoundException("User is not found")));
//
//        BigDecimal totalAmount = BigDecimal.ZERO;
//        for (TransactionItem item : items){
//            if (item.getTotalPrice() == null){
//                throw new RuntimeException("Total price of transaction is cannot be null");
//            }
//            totalAmount = totalAmount.add(BigDecimal.valueOf(item.getTotalPrice()));
//        }
//
//        if (voucherId != null){
//            Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(()-> new VoucherNotExistException("Voucher not exist"));
//            if (voucher.getDiscountPercentage() > 0){
//                BigDecimal discount = totalAmount.multiply(BigDecimal.valueOf(voucher.getDiscountPercentage()).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
//                totalAmount = totalAmount.subtract(discount);
//                trx.setVoucher(voucher);
//            }
//        }
//
//        trx.setTotalAmount(totalAmount);
//        Transaction savedTrx = transactionRepository.save(trx);
//
//        for (TransactionItem item : items){
//            item.setTransaction(savedTrx);
//            transactionItemRepository.save(item);
//        }
//        return savedTrx;
//    }
}

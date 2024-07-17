package com.eventhive.eventHive.Transaction.Service;

import com.eventhive.eventHive.Transaction.Dto.EventPurchaseInfo;
import com.eventhive.eventHive.Transaction.Dto.FreeEventTransactionDto;
import com.eventhive.eventHive.Transaction.Dto.TransactionRequestDto;
import com.eventhive.eventHive.Transaction.Dto.TransactionResponseDto;
import com.eventhive.eventHive.Transaction.Entity.Transaction;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto createTransaction(TransactionRequestDto requestDto);

    TransactionResponseDto createFreeEventTransaction(FreeEventTransactionDto dto);
    List<EventPurchaseInfo> getEventPurchaseInfo(Long userId);
}

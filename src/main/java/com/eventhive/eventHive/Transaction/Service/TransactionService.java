package com.eventhive.eventHive.Transaction.Service;

import com.eventhive.eventHive.Transaction.Dto.*;

public interface TransactionService {
    TransactionResponseDto createTransaction(TransactionRequestDto requestDto);
    TransactionResponseDto createFreeEventTransaction(FreeEventTransactionDto dto);
    EventPurchaseResult getEventPurchaseInfo();
}

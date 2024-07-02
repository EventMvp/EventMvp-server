package com.eventhive.eventHive.Transaction.Service;

import com.eventhive.eventHive.Transaction.Entity.Transaction;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Long userId, List<TransactionItem> items, Long voucherId);
}

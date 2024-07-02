package com.eventhive.eventHive.Transaction;

import com.eventhive.eventHive.Response.Response;
import com.eventhive.eventHive.Transaction.Service.TransactionService;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(
            @RequestParam Long userId,
            @RequestParam List<TransactionItem> items,
            @RequestParam(required = false) Long voucherId){
        return Response.successResponse("Transaction has been created", transactionService.createTransaction(userId, items, voucherId));
    }
}

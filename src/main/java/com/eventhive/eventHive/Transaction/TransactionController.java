package com.eventhive.eventHive.Transaction;

import com.eventhive.eventHive.Response.Response;
import com.eventhive.eventHive.Transaction.Dto.TransactionRequestDto;
import com.eventhive.eventHive.Transaction.Service.TransactionService;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody TransactionRequestDto requestDto
            )
    {
        return Response.successResponse("Transaction success", transactionService.createTransaction(requestDto));
    }
}

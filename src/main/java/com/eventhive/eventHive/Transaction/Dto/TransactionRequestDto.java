package com.eventhive.eventHive.Transaction.Dto;

import com.eventhive.eventHive.TransactionItem.Dto.TransactionItemRequestDto;
import lombok.Data;

import java.util.List;

@Data
public class TransactionRequestDto {
    private Long userId;
    private List<Long> voucherId;
    private List<TransactionItemRequestDto> items;
    private boolean usePoints;
}

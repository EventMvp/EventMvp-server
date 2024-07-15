package com.eventhive.eventHive.Transaction.Dto;

import com.eventhive.eventHive.TransactionItem.Dto.TransactionItemDto;
import com.eventhive.eventHive.Voucher.dto.VoucherDto;
import com.eventhive.eventHive.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionResponseDto {
    private Long transactionId;
    private BigDecimal totalAmount;
    private List<TransactionItemDto> transactionItems;
    private int pointUsed;
    private VoucherDto appliedVoucher;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public TransactionResponseDto() {
        this.createdAt = LocalDateTime.now();
    }
}

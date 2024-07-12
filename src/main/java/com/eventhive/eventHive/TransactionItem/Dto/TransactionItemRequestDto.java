package com.eventhive.eventHive.TransactionItem.Dto;

import lombok.Data;

@Data
public class TransactionItemRequestDto {
    private Long eventId;
    private Long ticketTypeId;
    private int quantity;
}

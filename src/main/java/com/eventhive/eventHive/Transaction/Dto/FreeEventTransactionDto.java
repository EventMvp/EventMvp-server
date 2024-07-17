package com.eventhive.eventHive.Transaction.Dto;

import lombok.Data;

@Data
public class FreeEventTransactionDto {
    private Long userId;
    private Long eventId;
    private Long eventTicketId;
}

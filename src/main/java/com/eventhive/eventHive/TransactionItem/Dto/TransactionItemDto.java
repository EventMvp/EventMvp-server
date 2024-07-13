package com.eventhive.eventHive.TransactionItem.Dto;

import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionItemDto {
    private Long id;
    private String ticketType;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    public static TransactionItemDto convertToDto(TransactionItem item){
        TransactionItemDto dto = new TransactionItemDto();
        dto.setId(item.getId());
        dto.setTicketType(item.getEventTicket().getTicketType().getTypeName());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getPrice());
        dto.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        return dto;
    }
}

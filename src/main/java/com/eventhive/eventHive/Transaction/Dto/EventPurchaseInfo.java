package com.eventhive.eventHive.Transaction.Dto;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Transaction.Entity.Transaction;
import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import com.eventhive.eventHive.utils.LocalDateSerializer;
import com.eventhive.eventHive.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class EventPurchaseInfo {
    private Long transactionId;
    private Long eventId;
    private String eventTitle;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate eventDate;
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime eventTime;
    private String eventLocation;
    private String eventPicture;
    private BigDecimal totalAmount;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime purchaseDate;
    private List<TicketInfo> tickets;

    @Data
    @Builder
    public static class TicketInfo {
        private String ticketType;
        private int quantity;
        private BigDecimal price;
        private BigDecimal totalPrice;
    }

    public static EventPurchaseInfo mapToEventPurchaseInfo(Transaction transaction) {
        Events event = transaction.getEvents();

        return EventPurchaseInfo.builder()
                .transactionId(transaction.getId())
                .eventId(event.getId())
                .eventTitle(event.getTitle())
                .eventDate(event.getDate())
                .eventTime(event.getTime())
                .eventLocation(event.getLocation())
                .eventPicture(event.getPicture())
                .totalAmount(transaction.getTotalAmount())
                .purchaseDate(transaction.getCreatedAt())
                .tickets(mapToTicketInfo(transaction.getTransactionItems()))
                .build();
    }

    public static List<TicketInfo> mapToTicketInfo(List<TransactionItem> transactionItems) {
        return transactionItems.stream()
                .map(item -> TicketInfo.builder()
                        .ticketType(item.getEventTicket().getTicketTypeStr())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
    }
}

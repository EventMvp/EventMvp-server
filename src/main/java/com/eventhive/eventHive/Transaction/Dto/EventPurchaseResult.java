package com.eventhive.eventHive.Transaction.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventPurchaseResult {
    private List<EventPurchaseInfo> upcomingEvents;
    private List<EventPurchaseInfo> pastEvent;
}

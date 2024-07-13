package com.eventhive.eventHive.PointHistory.Service;

import com.eventhive.eventHive.Users.Entity.Users;

import java.math.BigDecimal;

public interface PointHistoryService {
    void awardsPoints(Users user, int points);

    public int redeemPoints(Long userId, BigDecimal totalPriceTicket);
    int totalActivePoints(Long userId);
}

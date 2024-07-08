package com.eventhive.eventHive.PointHistory.Service;

import com.eventhive.eventHive.Users.Entity.Users;

public interface PointHistoryService {
    void awardsPoints(Users user, int points);

    void redeemPoints(Users user, int points);

    int totalActivePoints(Long userId);
}

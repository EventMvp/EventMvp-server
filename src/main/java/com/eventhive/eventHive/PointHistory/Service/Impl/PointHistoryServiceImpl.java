package com.eventhive.eventHive.PointHistory.Service.Impl;

import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import com.eventhive.eventHive.PointHistory.Repository.PointHistoryRepository;
import com.eventhive.eventHive.PointHistory.Service.PointHistoryService;
import com.eventhive.eventHive.Users.Entity.Users;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository repository;

    public PointHistoryServiceImpl(PointHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void awardsPoints(Users users) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.setUser(users);
        pointHistory.setPoints(10000);
        pointHistory.setExpiryAt(LocalDate.now().plusMonths(3));
        repository.save(pointHistory);
    }
}

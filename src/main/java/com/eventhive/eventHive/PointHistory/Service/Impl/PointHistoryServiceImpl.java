package com.eventhive.eventHive.PointHistory.Service.Impl;

import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import com.eventhive.eventHive.PointHistory.Repository.PointHistoryRepository;
import com.eventhive.eventHive.PointHistory.Service.PointHistoryService;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository repository;
    private final UsersRepository usersRepository;

    public PointHistoryServiceImpl(PointHistoryRepository repository, UsersRepository usersRepository) {
        this.repository = repository;
        this.usersRepository = usersRepository;
    }

    @Override
    public void awardsPoints(Users user, int points) {
        PointHistory pointHistory = new PointHistory();
        pointHistory.setUser(user);
        pointHistory.setPoints(points);
        pointHistory.setExpiryAt(LocalDate.now().plusMonths(3));
        pointHistory.setStatus(PointHistory.PointsStatus.ACTIVE);
        repository.save(pointHistory);

        user.setPoints(user.getPoints() + points);
        usersRepository.save(user);
    }

    @Override
    public void redeemPoints(Users user, int points) {
        if (user.getPoints() < points){
            throw new IllegalArgumentException("Insufficient points balance");
        }
        PointHistory pointsHistory = new PointHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPoints(-points);
        pointsHistory.setStatus(PointHistory.PointsStatus.REDEEMED);
        repository.save(pointsHistory);

        user.setPoints(user.getPoints() - points);
        usersRepository.save(user);
    }

}

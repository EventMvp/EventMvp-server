package com.eventhive.eventHive.PointHistory.Service.Impl;

import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import com.eventhive.eventHive.PointHistory.Repository.PointHistoryRepository;
import com.eventhive.eventHive.PointHistory.Service.PointHistoryService;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        repository.save(pointHistory);

        user.setPoints(user.getPoints() + points);
        usersRepository.save(user);
    }

    @Override
    public void redeemPoints(Users user, int points) {
        //Check only active points to subtract
        List<PointHistory> listActivePoints = repository.findActivePointByUserId(user.getId(), LocalDateTime.now());
        int totalActivePoints = listActivePoints.stream().mapToInt(PointHistory::getPoints).sum();

        if (totalActivePoints < points){
            throw new IllegalArgumentException("Insufficient points balance");
        }

        int pointToDeduct = points;
        for (PointHistory history : listActivePoints){
            if (pointToDeduct <= 0){
                break;
            }

        }

        PointHistory pointsHistory = new PointHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPoints(-points);
        repository.save(pointsHistory);

        user.setPoints(user.getPoints() - points);
        usersRepository.save(user);
    }

}

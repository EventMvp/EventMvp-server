package com.eventhive.eventHive.PointHistory.Service.Impl;

import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import com.eventhive.eventHive.PointHistory.Repository.PointHistoryRepository;
import com.eventhive.eventHive.PointHistory.Service.PointHistoryService;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public int redeemPoints(Long userId, BigDecimal totalPriceTicket) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User is not exist"));
        //Check only active points to subtract
        List<PointHistory> listActivePoints = repository.findActivePointByUserId(user.getId(), LocalDate.now());
        int totalActivePoints = listActivePoints.stream().mapToInt(PointHistory::getPoints).sum();

        BigDecimal pointValue = BigDecimal.valueOf(totalActivePoints);
        int pointToRedeem;
        if (pointValue.compareTo(totalPriceTicket) > 0){
            pointToRedeem = totalPriceTicket.intValue();
        } else {
            pointToRedeem = totalActivePoints;
        }

        //deduct point from user
        user.setPoints(user.getPoints() - pointToRedeem);
        usersRepository.save(user);

        //create a new point history
        PointHistory pointHistory = new PointHistory();
        pointHistory.setUser(user);
        pointHistory.setPoints(-pointToRedeem);

        return pointToRedeem;

    }

    @Override
    public int totalActivePoints(Long userId) {
        LocalDate now = LocalDate.now();
        List<PointHistory> pointHistoryList = repository.findActivePointByUserId(userId, now);

        return pointHistoryList.stream()
                .mapToInt(PointHistory::getPoints).sum();
    }

}

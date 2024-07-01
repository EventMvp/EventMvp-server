package com.eventhive.eventHive.PointHistory.Repository;

import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findByUserIdAndExpiryAt(Long userId, LocalDate date);
}

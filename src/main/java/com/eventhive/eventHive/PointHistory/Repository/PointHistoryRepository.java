package com.eventhive.eventHive.PointHistory.Repository;

import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    @Query("SELECT p from PointHistory p WHERE p.user.id = :userId AND p.expiryAt > :now AND p.points > 0")
    List<PointHistory> findActivePointByUserId(
            @Param("userId") Long userId,
            @Param("now")LocalDateTime now
            );
}

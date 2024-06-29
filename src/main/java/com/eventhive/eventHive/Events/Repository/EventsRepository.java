package com.eventhive.eventHive.Events.Repository;

import com.eventhive.eventHive.Events.Entity.Events;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface EventsRepository extends JpaRepository<Events, Long> {
    @Query("SELECT e FROM Events e WHERE " +
            "(:categoryId IS NULL OR e.category.id = :categoryId) AND " +
            "(:date IS NULL OR e.date = :date) AND " +
            "(:isFree IS NULL OR " +
            "(SELECT COUNT(t) FROM EventTicket t WHERE t.event = e AND " +
            "(CASE WHEN :isFree = TRUE THEN t.price = 0 ELSE t.price > 0 END)) > 0) AND " +
            "(SELECT SUM(t.availableSeats) FROM EventTicket t WHERE t.event = e) > 0")
    Page<Events> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("date") LocalDate date,
            @Param("isFree") Boolean isFree,
            Pageable pageable
            );
}

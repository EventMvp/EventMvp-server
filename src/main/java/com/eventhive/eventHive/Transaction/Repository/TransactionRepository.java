package com.eventhive.eventHive.Transaction.Repository;

import com.eventhive.eventHive.Transaction.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.user.id = :userId AND t.events.date >= :currentDate
            ORDER BY t.events.date ASC
            """)
    List<Transaction> findUpcomingTransactionsByUserId(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);
    @Query("""
            SELECT t
            FROM Transaction t
            WHERE t.user.id = :userId AND t.events.date < :currentDate
            ORDER BY t.events.date DESC
            """)
    List<Transaction> findPastTransactionsByUserId(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);
    List<Transaction> findByUserId(Long userId);
}

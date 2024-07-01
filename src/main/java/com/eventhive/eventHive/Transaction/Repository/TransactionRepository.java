package com.eventhive.eventHive.Transaction.Repository;

import com.eventhive.eventHive.Transaction.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

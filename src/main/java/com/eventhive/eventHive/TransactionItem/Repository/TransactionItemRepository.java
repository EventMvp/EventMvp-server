package com.eventhive.eventHive.TransactionItem.Repository;

import com.eventhive.eventHive.TransactionItem.Entity.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
}

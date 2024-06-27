package com.eventhive.eventHive.Events.Repository;

import com.eventhive.eventHive.Events.Entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Events, Long> {
}

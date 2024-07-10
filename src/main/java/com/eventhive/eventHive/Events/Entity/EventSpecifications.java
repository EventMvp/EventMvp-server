package com.eventhive.eventHive.Events.Entity;

import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class EventSpecifications {
    public static Specification<Events> withFilters(Long categoryId, LocalDate startDate, LocalDate endDate, Boolean isFree){
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (categoryId != null){
                predicate = cb.and(predicate, cb.equal(root.get("category").get("id"), categoryId));
            }

            if (startDate != null && endDate != null){
                predicate = cb.and(predicate, cb.between(root.get("date"), startDate, endDate));
            }

            if (isFree != null) {
                Subquery<Long> ticketSubquery = query.subquery(Long.class);
                Root<EventTicket> ticketRoot = ticketSubquery.from(EventTicket.class);
                ticketSubquery.select(cb.count(ticketRoot))
                        .where(cb.equal(ticketRoot.get("event"), root),
                                isFree ? cb.equal(ticketRoot.get("price"), 0) : cb.greaterThan(ticketRoot.get("price"), 0));
                predicate = cb.and(predicate, cb.greaterThan(ticketSubquery, 0L));
            }

            Subquery<Long> availableSeatsSubquery = query.subquery(Long.class);
            Root<EventTicket> availableSeatsRoot = availableSeatsSubquery.from(EventTicket.class);
            availableSeatsSubquery.select(cb.sum(availableSeatsRoot.get("availableSeats")))
                    .where(cb.equal(availableSeatsRoot.get("event"), root));
            predicate = cb.and(predicate, cb.greaterThan(availableSeatsSubquery, 0L));

            return predicate;
        };
    }
}

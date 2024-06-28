package com.eventhive.eventHive.EventTicket.Entity;

import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Events.Entity.TicketType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "event_ticket", schema = "public")
public class EventTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Events event;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Column(name = "available_seats")
    private int availableSeats;

    @Column(name = "price")
    private BigDecimal price;
}

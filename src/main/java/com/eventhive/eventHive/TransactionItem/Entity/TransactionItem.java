package com.eventhive.eventHive.TransactionItem.Entity;

import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.TicketType.Entity.TicketType;
import com.eventhive.eventHive.Transaction.Entity.Transaction;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction_items", schema = "public")
public class TransactionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Events event;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id", referencedColumnName = "id")
    private TicketType ticketType;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
}

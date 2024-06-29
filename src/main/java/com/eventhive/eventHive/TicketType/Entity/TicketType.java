package com.eventhive.eventHive.TicketType.Entity;

import com.eventhive.eventHive.Users.Entity.Users;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ticket_types", schema = "public")
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;
}
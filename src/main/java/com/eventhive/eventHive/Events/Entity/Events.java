package com.eventhive.eventHive.Events.Entity;

import com.eventhive.eventHive.Category.Entity.Category;
import com.eventhive.eventHive.EventTicket.Entity.EventTicket;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Voucher.Entity.Voucher;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "events", schema = "public", indexes = {
        @Index(name = "idx_event_title", columnList = "title")
})
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "picture")
    private String picture;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
    private List<EventTicket> eventTickets;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private Users organizer;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Voucher> vouchers;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public boolean isFreeEvent(){
        return eventTickets != null && eventTickets.stream().allMatch(ticket -> ticket.getPrice().compareTo(BigDecimal.ZERO) == 0);
    }
}


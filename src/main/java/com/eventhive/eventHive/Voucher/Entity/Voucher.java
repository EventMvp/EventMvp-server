package com.eventhive.eventHive.Voucher.Entity;

import com.eventhive.eventHive.Events.Entity.Events;
import com.eventhive.eventHive.Users.Entity.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "voucher", schema = "public")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "voucher_name", nullable = false)
    private String name;

    @Column(name = "discount_percentage", nullable = false)
    private int discountPercentage;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Events event;

    @ManyToOne
    @JoinColumn(name = "organizer_id", referencedColumnName = "id")
    private Users organizer;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_referral_based", nullable = false)
    private boolean isReferralBased = false;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

}

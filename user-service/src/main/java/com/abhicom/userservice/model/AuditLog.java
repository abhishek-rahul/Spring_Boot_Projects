package com.abhicom.userservice.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long orderId;

    @Column(nullable = false, length = 200)
    private String action;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected AuditLog() {}

    public AuditLog(Long userId, Long orderId, String action) {
        this.userId = userId;
        this.orderId = orderId;
        this.action = action;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getOrderId() { return orderId; }
    public String getAction() { return action; }
    public Instant getCreatedAt() { return createdAt; }
}

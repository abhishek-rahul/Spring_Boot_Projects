package com.abhicom.userservice.dto;


import com.abhicom.userservice.model.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;

public class OrderRowDto {
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Instant createdAt;

    // getters & setters
}

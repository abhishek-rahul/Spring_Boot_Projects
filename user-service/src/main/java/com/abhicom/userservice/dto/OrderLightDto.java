package com.abhicom.userservice.dto;

import com.abhicom.userservice.model.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;

public class OrderLightDto {

    private final Long orderId;
    private final OrderStatus status;
    private final BigDecimal totalAmount;
    private final Instant createdAt;
    private final String userEmail;

    public OrderLightDto(Long orderId, OrderStatus status, BigDecimal totalAmount, Instant createdAt, String userEmail) {
        this.orderId = orderId;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.userEmail = userEmail;
    }

    public Long getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Instant getCreatedAt() { return createdAt; }
    public String getUserEmail() { return userEmail; }
}

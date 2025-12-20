package com.abhicom.userservice.dto;

import com.abhicom.userservice.model.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;

public class OrderSearchRowDto {
    private Long orderId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Instant createdAt;

    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public String getUserFirstName() {
        return userFirstName;
    }
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }
    public String getUserLastName() {
        return userLastName;
    }
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // getters & setters
}

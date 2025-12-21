package com.abhicom.userservice.dto;

import java.time.Instant;

public class UserSummaryDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private long ordersCount;
    private Instant latestOrderDate;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public long getOrdersCount() {
        return ordersCount;
    }
    public void setOrdersCount(long ordersCount) {
        this.ordersCount = ordersCount;
    }
    public Instant getLatestOrderDate() {
        return latestOrderDate;
    }
    public void setLatestOrderDate(Instant latestOrderDate) {
        this.latestOrderDate = latestOrderDate;
    }

}

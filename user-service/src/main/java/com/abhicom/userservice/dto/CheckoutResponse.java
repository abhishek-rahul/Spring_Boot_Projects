package com.abhicom.userservice.dto;

public class CheckoutResponse {
    private Long userId;
    private Long orderId;
    private String message;

    public CheckoutResponse(Long userId, Long orderId, String message) {
        this.userId = userId;
        this.orderId = orderId;
        this.message = message;
    }

    public Long getUserId() { return userId; }
    public Long getOrderId() { return orderId; }
    public String getMessage() { return message; }
}

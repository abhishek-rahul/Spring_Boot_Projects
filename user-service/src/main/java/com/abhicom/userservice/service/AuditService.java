package com.abhicom.userservice.service;

public interface AuditService {
    void logCheckoutAttempt(Long userId, Long orderId, String action);
}

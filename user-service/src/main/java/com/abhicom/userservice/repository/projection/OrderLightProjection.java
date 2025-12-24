package com.abhicom.userservice.repository.projection;

import com.abhicom.userservice.model.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;

public interface OrderLightProjection {
    Long getOrderId();
    OrderStatus getStatus();
    BigDecimal getTotalAmount();
    Instant getCreatedAt();
    String getUserEmail();
}

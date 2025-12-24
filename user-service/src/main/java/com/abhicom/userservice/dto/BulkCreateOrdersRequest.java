package com.abhicom.userservice.dto;

import com.abhicom.userservice.model.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BulkCreateOrdersRequest {

    @NotEmpty
    private List<OrderCreate> orders;

    public List<OrderCreate> getOrders() { return orders; }
    public void setOrders(List<OrderCreate> orders) { this.orders = orders; }

    public static class OrderCreate {
        @NotNull
        private Long userId;

        @NotNull
        private OrderStatus status;

        @NotEmpty
        private List<ItemCreate> items;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }

        public List<ItemCreate> getItems() { return items; }
        public void setItems(List<ItemCreate> items) { this.items = items; }
    }

    public static class ItemCreate {
        @NotNull
        private String sku;

        @NotNull
        private Integer quantity;

        @NotNull
        private java.math.BigDecimal price;

        public String getSku() { return sku; }
        public void setSku(String sku) { this.sku = sku; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public java.math.BigDecimal getPrice() { return price; }
        public void setPrice(java.math.BigDecimal price) { this.price = price; }
    }
}

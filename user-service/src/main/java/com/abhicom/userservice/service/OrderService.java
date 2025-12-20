package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.OrderSearchRowDto;
import com.abhicom.userservice.model.Orders;
import com.abhicom.userservice.model.OrderStatus;
import com.abhicom.userservice.repository.OrderRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<OrderSearchRowDto> search(
            OrderStatus status,
            String email,
            Instant from,
            Instant to,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            int page,
            int size,
            String sortBy,
            String sortDir,
            boolean useNative
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Orders> result = useNative
                ? orderRepository.searchOrdersNative(
                    status == null ? null : status.name(),email,
                    from, to, minAmount, maxAmount, pageable
                )
                : orderRepository.searchOrdersJpql(
                    status, email, from, to, minAmount, maxAmount, pageable
                );

        return result.map(this::toRowDto);
    }

    private OrderSearchRowDto toRowDto(Orders o) {
        OrderSearchRowDto dto = new OrderSearchRowDto();
        dto.setOrderId(o.getId());
        dto.setStatus(o.getStatus());
        dto.setTotalAmount(o.getTotalAmount());
        dto.setCreatedAt(o.getCreatedAt());

        dto.setUserId(o.getUser().getId());
        dto.setUserFirstName(o.getUser().getFirstName());
        dto.setUserLastName(o.getUser().getLastName());
        dto.setUserEmail(o.getUser().getEmail());
        return dto;
    }
}

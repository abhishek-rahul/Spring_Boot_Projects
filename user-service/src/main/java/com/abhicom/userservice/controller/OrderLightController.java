package com.abhicom.userservice.controller;

import com.abhicom.userservice.dto.OrderLightDto;
import com.abhicom.userservice.model.OrderStatus;
import com.abhicom.userservice.repository.OrderRepository;
import com.abhicom.userservice.repository.projection.OrderLightProjection;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderLightController {

    private final OrderRepository orderRepository;

    public OrderLightController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/light")
    public ResponseEntity<?> lightOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String email,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,

            @RequestParam(defaultValue = "interface") String mode
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDir), sortBy));

        if ("dto".equalsIgnoreCase(mode)) {
            Page<OrderLightDto> dtoPage = orderRepository.findLightOrdersDto(status, email, pageable);
            return ResponseEntity.ok(dtoPage);
        }

        Page<OrderLightProjection> projectionPage = orderRepository.findLightOrdersInterface(status, email, pageable);
        return ResponseEntity.ok(projectionPage);
    }
}

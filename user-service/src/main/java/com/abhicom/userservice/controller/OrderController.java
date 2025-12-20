package com.abhicom.userservice.controller;

import com.abhicom.userservice.dto.OrderSearchRowDto;
import com.abhicom.userservice.model.OrderStatus;
import com.abhicom.userservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<OrderSearchRowDto>> search(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,

            @RequestParam(defaultValue = "false") boolean useNative
    ) {
        return ResponseEntity.ok(
                orderService.search(status, email, from, to, minAmount, maxAmount,
                        page, size, sortBy, sortDir, useNative)
        );
    }
}

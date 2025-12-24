package com.abhicom.userservice.controller;

import com.abhicom.userservice.dto.BulkCreateOrdersRequest;
import com.abhicom.userservice.service.OrderBulkService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderBulkController {

    private final OrderBulkService orderBulkService;

    public OrderBulkController(OrderBulkService orderBulkService) {
        this.orderBulkService = orderBulkService;
    }

    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> bulkCreate(@Valid @RequestBody BulkCreateOrdersRequest req) {
        int created = orderBulkService.bulkCreateOrders(req);
        return ResponseEntity.ok(Map.of(
                "createdOrders", created
        ));
    }
}

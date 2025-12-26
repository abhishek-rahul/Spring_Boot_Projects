package com.abhicom.userservice.controller;

import com.abhicom.userservice.dto.CheckoutRequest;
import com.abhicom.userservice.dto.CheckoutResponse;
import com.abhicom.userservice.exception.CheckoutCheckedException;
import com.abhicom.userservice.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<CheckoutResponse> checkout(
            @PathVariable("id") Long userId,
            @Valid @RequestBody CheckoutRequest request
    ) throws CheckoutCheckedException {

        CheckoutResponse response = checkoutService.checkout(userId, request);
        return ResponseEntity.ok(response);
    }
}

package com.abhicom.userservice.service.impl;

import com.abhicom.userservice.dto.CheckoutRequest;
import com.abhicom.userservice.dto.CheckoutResponse;
import com.abhicom.userservice.exception.CheckoutCheckedException;
import com.abhicom.userservice.model.User;
import com.abhicom.userservice.repository.UserRepository;
import com.abhicom.userservice.service.AuditService;
import com.abhicom.userservice.service.CheckoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final UserRepository userRepository;
    private final AuditService auditService;

    public CheckoutServiceImpl(UserRepository userRepository, AuditService auditService) {
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    @Override
    public CheckoutResponse checkout(Long userId, CheckoutRequest request) throws CheckoutCheckedException {
        // Choose which transactional method to call (for checked rollback demo)
        if (request.getFailureMode() == CheckoutRequest.FailureMode.CHECKED && request.isRollbackOnChecked()) {
            return checkoutRollbackOnChecked(userId, request);
        }
        return checkoutDefault(userId, request);
    }

    /**
     * DEFAULT RULE:
     * - RuntimeException => rollback
     * - Checked Exception => NO rollback (by default)
     */
    @Transactional
    public CheckoutResponse checkoutDefault(Long userId, CheckoutRequest request) throws CheckoutCheckedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Update user inside txn (dirty checking will flush on commit)
        user.setLastCheckoutAt(Instant.now()); // you will add this field in User (step below)

        // Pretend "orderId" is generated somewhere; to keep this step self-contained,
        // we’ll use a simple surrogate: store audit with null orderId for now IF you don't have Order entity yet.
        // If you already have Order entity in your project, replace this with real order save and pass orderId.

        Long fakeOrderId = System.currentTimeMillis(); // demo only (replace with real Order.id)
        auditService.logCheckoutAttempt(userId, fakeOrderId, "CHECKOUT_STARTED");

        // Failure simulation
        if (request.getFailureMode() == CheckoutRequest.FailureMode.RUNTIME) {
            throw new IllegalStateException("Simulated runtime failure => SHOULD ROLLBACK outer transaction");
        }
        if (request.getFailureMode() == CheckoutRequest.FailureMode.CHECKED) {
            throw new CheckoutCheckedException("Simulated checked failure => DEFAULT: will NOT rollback outer transaction");
        }

        auditService.logCheckoutAttempt(userId, fakeOrderId, "CHECKOUT_SUCCESS");
        return new CheckoutResponse(userId, fakeOrderId, "Checkout completed (default tx rules).");
    }

    /**
     * CHANGED RULE:
     * - Checked Exception => rollback too (because rollbackFor is set)
     */
    @Transactional(rollbackFor = CheckoutCheckedException.class)
    public CheckoutResponse checkoutRollbackOnChecked(Long userId, CheckoutRequest request) throws CheckoutCheckedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        user.setLastCheckoutAt(Instant.now());

        Long fakeOrderId = System.currentTimeMillis(); // demo only (replace with real Order.id)
        auditService.logCheckoutAttempt(userId, fakeOrderId, "CHECKOUT_STARTED_STRICT");

        // Now checked exception WILL rollback
        throw new CheckoutCheckedException("Checked failure with rollbackFor => SHOULD ROLLBACK outer transaction");
    }
}

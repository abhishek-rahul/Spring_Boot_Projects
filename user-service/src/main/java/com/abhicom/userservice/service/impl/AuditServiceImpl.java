package com.abhicom.userservice.service.impl;

import com.abhicom.userservice.model.AuditLog;
import com.abhicom.userservice.repository.AuditLogRepository;
import com.abhicom.userservice.service.AuditService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void logCheckoutAttempt(Long userId, Long orderId, String action) {
        // This will commit even if outer transaction rolls back
        auditLogRepository.save(new AuditLog(userId, orderId, action));
    }
}

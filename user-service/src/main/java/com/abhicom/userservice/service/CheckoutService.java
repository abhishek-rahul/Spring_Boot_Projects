package com.abhicom.userservice.service;

import com.abhicom.userservice.dto.CheckoutRequest;
import com.abhicom.userservice.dto.CheckoutResponse;
import com.abhicom.userservice.exception.CheckoutCheckedException;

public interface CheckoutService {
    CheckoutResponse checkout(Long userId, CheckoutRequest request) throws CheckoutCheckedException;
}

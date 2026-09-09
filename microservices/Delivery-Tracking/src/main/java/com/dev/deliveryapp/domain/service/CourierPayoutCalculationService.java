package com.dev.deliveryapp.domain.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface CourierPayoutCalculationService {
    BigDecimal calculatePayout(Double distanceInkm);
}

package com.dev.deliveryapp.domain.service;

import com.dev.deliveryapp.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {
    DeliveryEstimate estimate(ContactPoint sender,ContactPoint receiver);
}

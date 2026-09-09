package com.dev.deliveryapp.infra.Emulation;

import com.dev.deliveryapp.domain.model.ContactPoint;
import com.dev.deliveryapp.domain.service.DeliveryEstimate;
import com.dev.deliveryapp.domain.service.DeliveryTimeEstimationService;
import org.springframework.stereotype.Service;

import java.time.Duration;
@Service
public class DeliveryTimeEstimateEmulation implements DeliveryTimeEstimationService {

    @Override
    public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
        return new DeliveryEstimate(
                Duration.ofHours(3),3.1
        );
    }
}

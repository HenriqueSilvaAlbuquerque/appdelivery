package com.dev.appdelivery.domain.service;

import com.dev.appdelivery.api.model.CourierInput;
import com.dev.appdelivery.domain.model.Courier;
import com.dev.appdelivery.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CourierRegistrationService {
    private final CourierRepository courierRepository;
    
    public Courier create(CourierInput courierInput) {
        Courier courier = Courier.brandNew(courierInput.getName(), courierInput.getPhone());
        return courierRepository.saveAndFlush(courier);
    }

    public Courier update(UUID courierId, CourierInput courierInput) {
        Courier courier = courierRepository.findById(courierId).orElseThrow();
        courier.setName(courierInput.getName());
        courier.setPhone(courierInput.getPhone());
        return courierRepository.saveAndFlush(courier);

    }
}

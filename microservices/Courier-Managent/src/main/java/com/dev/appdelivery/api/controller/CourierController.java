package com.dev.appdelivery.api.controller;

import com.dev.appdelivery.api.model.CourierInput;
import com.dev.appdelivery.api.model.CourierPayoutCalculationInput;
import com.dev.appdelivery.api.model.CourierPayoutResultModel;
import com.dev.appdelivery.domain.model.Courier;
import com.dev.appdelivery.domain.repository.CourierRepository;
import com.dev.appdelivery.domain.service.CourierPayoutService;
import com.dev.appdelivery.domain.service.CourierRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
public class CourierController {
    private final CourierRegistrationService courierRegistrationService;
    private final CourierRepository courierRepository;
    private final CourierPayoutService courierPayoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Courier create(@Valid @RequestBody CourierInput courierInput){
        return courierRegistrationService.create(courierInput);
    }

    @PutMapping("/{courierId}")
    public Courier update(@PathVariable UUID courierId,@Valid @RequestBody CourierInput courierInput){
        return courierRegistrationService.update(courierId,courierInput);
    }

    @GetMapping
    public PagedModel<Courier>findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId){
        return courierRepository.findById(courierId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @RequestMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input ){
        BigDecimal payoutFee=courierPayoutService.calculate(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }

}

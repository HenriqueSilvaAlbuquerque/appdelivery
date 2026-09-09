package com.dev.deliveryapp.domain.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
@Getter
@AllArgsConstructor
public class DeliveryEstimate {
    private Duration estimateTime;
    private Double distanceInKm;
}

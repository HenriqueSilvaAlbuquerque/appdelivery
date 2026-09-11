package com.dev.deliveryapp.infra.http.client;

import com.dev.deliveryapp.domain.service.CourierPayoutCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {
    private final CourierApiClient courierApiClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInkm) {
        CourierPayoutResultModel courierPayoutResultModel = courierApiClient.payoutCalculation(new CourierPayoutCalculationInput(distanceInkm));
        return courierPayoutResultModel.getPayoutFee();
    }
}

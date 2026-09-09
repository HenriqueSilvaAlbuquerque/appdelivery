package com.dev.deliveryapp.domain.service;

import com.dev.deliveryapp.api.model.ContactPointInput;
import com.dev.deliveryapp.api.model.DeliveryInput;
import com.dev.deliveryapp.api.model.ItemInput;
import com.dev.deliveryapp.domain.model.ContactPoint;
import com.dev.deliveryapp.domain.model.Delivery;
import com.dev.deliveryapp.domain.model.excepetion.DomainException;
import com.dev.deliveryapp.domain.repository.DeliveryRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryPrepararionService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryTimeEstimationService deliveryTimeEstimationService;
    private final CourierPayoutCalculationService courierPayoutCalculationService;

    @Transactional
    public Delivery draft(DeliveryInput input){
        Delivery delivery = Delivery.draft();
        handlePreparation(input,delivery);
        return deliveryRepository.saveAndFlush(delivery);
    }
    @Transactional
    public Delivery edit( UUID deliveryId,DeliveryInput input){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException());
        delivery.removeItens();
        handlePreparation(input,delivery);
        return deliveryRepository.saveAndFlush(delivery);


    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) {
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                .phone(senderInput.getPhone())
                .name(senderInput.getName())
                .complement(senderInput.getComplement())
                .number(senderInput.getNumber())
                .zipCode(senderInput.getZipCode())
                .street(senderInput.getStreet())
                .build();
        
        ContactPoint recipient = ContactPoint.builder()
                .phone(recipientInput.getPhone())
                .name(recipientInput.getName())
                .complement(recipientInput.getComplement())
                .number(recipientInput.getNumber())
                .zipCode(recipientInput.getZipCode())
                .street(recipientInput.getStreet())
                .build();

        DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
        BigDecimal calculatePayout = courierPayoutCalculationService.calculatePayout(estimate.getDistanceInKm());
        BigDecimal distanceFee=calculateFee(estimate.getDistanceInKm());


        Delivery.PreparationDetails preparationDetails = Delivery.PreparationDetails.builder()
                .recipient(recipient)
                .sender(sender)
                .expectedDeliveryTime(estimate.getEstimateTime())
                .CourierPayout(calculatePayout)
                .distanceFee(distanceFee)
                .build();

        delivery.editPreparationDetails(preparationDetails);

        for (ItemInput item: input.getItems()){
            delivery.addItem(item.getName(), item.getQuantity());
        }
    }


    private BigDecimal calculateFee(Double distanceInKm) {
        return new BigDecimal("3")
                .multiply(BigDecimal.valueOf(distanceInKm))
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}

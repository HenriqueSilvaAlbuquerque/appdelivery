package com.dev.deliveryapp.domain.model;

import com.dev.deliveryapp.domain.model.excepetion.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest { 
    
    @Test
    public void shouldChangeToPlaced(){
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER,delivery.getDeliveryStatus());
        assertNotNull(delivery.getPlacedAt());

    }

    @Test
    public void shoulNotToPlaced(){
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class,()->{ delivery.place();});

        assertEquals(DeliveryStatus.DRAFT,delivery.getDeliveryStatus());
        assertNull(delivery.getPlacedAt());

    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender= ContactPoint.builder()
                .zipCode("0001")
                .street("rua das aves")
                .number("123")
                .complement("casa")
                .nome("caio nunes")
                .phone("123456789")
                .build();

        ContactPoint recipent= ContactPoint.builder()
                .zipCode("00545484")
                .street("rua das patos")
                .number("158")
                .complement("apartamento torre2 numero 89")
                .nome("Mario Silva")
                .phone("123452584")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipent)
                .distanceFee(new BigDecimal("15.00"))
                .CourierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}
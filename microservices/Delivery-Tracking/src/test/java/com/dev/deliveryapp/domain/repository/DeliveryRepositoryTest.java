package com.dev.deliveryapp.domain.repository;

import com.dev.deliveryapp.domain.model.ContactPoint;
import com.dev.deliveryapp.domain.model.Delivery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class DeliveryRepositoryTest {

    @Autowired
    private DeliveryRepository deliveryRepository;
    @Test
    public void shouldPersist(){
        Delivery delivery = Delivery.draft();
        delivery.editPreparationDetails(createValidPreparationDetails());
        delivery.addItem("computador",3);
        delivery.addItem("mouse",2);
        deliveryRepository.saveAndFlush(delivery);

        Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();
        assertEquals(2,persistedDelivery.getItems().size());



    }

    private Delivery.PreparationDetails createValidPreparationDetails() {
        ContactPoint sender= ContactPoint.builder()
                .zipCode("0001")
                .street("rua das aves")
                .number("123")
                .complement("casa")
                .name("caio nunes")
                .phone("123456789")
                .build();

        ContactPoint recipent= ContactPoint.builder()
                .zipCode("00545484")
                .street("rua das patos")
                .number("158")
                .complement("apartamento torre2 numero 89")
                .name("Mario Silva")
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

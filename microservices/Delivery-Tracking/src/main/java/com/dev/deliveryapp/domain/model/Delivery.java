package com.dev.deliveryapp.domain.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access= AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Delivery {
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID courierId;
    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fullfilledAt;
    private ContatctPoint sender;
    private ContatctPoint recipient;
    private BigDecimal distancieFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;
    private Integer totalItems;
    private DeliveryStatus deliveryStatus;
    private List<Item> items=new ArrayList<>();


    public static Delivery draft(){
        Delivery delivery = new Delivery();
        delivery.id=UUID.randomUUID();
        delivery.deliveryStatus=DeliveryStatus.DRAFT;
        delivery.totalItems=0;
        delivery.totalCost=BigDecimal.ZERO;
        delivery.courierPayout=BigDecimal.ZERO;
        delivery.distancieFee=BigDecimal.ZERO;
        return delivery;
    }



}

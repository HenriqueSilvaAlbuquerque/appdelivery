package com.dev.deliveryapp.domain.model;

import com.dev.deliveryapp.domain.model.excepetion.DomainException;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access= AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(AccessLevel.PRIVATE)
@Getter
public class Delivery {
    @EqualsAndHashCode.Include
    private UUID id;
    private UUID courierId;
    private OffsetDateTime placedAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;
    private ContactPoint sender;
    private ContactPoint recipient;
    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;
    private Integer totalItems;
    private DeliveryStatus deliveryStatus;
    private List<Item> items =new ArrayList<>();


    public static Delivery draft(){
        Delivery delivery = new Delivery();
        delivery.setCourierId(UUID.randomUUID());
        delivery.setDeliveryStatus(DeliveryStatus.DRAFT);
        delivery.setTotalItems(0);
        delivery.setTotalCost(BigDecimal.ZERO);
        delivery.setCourierPayout(BigDecimal.ZERO);
        delivery.setDistanceFee(BigDecimal.ZERO);
        return delivery;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }


    public UUID addItem(String name, int quantity){
        Item item = Item.brandNew(name, quantity);
        this.items.add(item);
        calculateTotalItens();
        return item.getId();
    }

    public void removeItem(UUID itemId){
        items.removeIf(item -> item.getId().equals(itemId));
        calculateTotalItens();
    }

    public void removeItens(){
        items.clear();
        calculateTotalItens();

    }

    public void editPreparationDetails(PreparationDetails details){
        verifyCanBeEdited();
        setSender(details.sender);
        setRecipient(details.recipient);
        setDistanceFee(details.distanceFee);
        setCourierPayout(details.getCourierPayout());
        setExpectedDeliveryAt(OffsetDateTime.now().plus(details.getExpectedDeliveryTime()));
        setTotalCost(this.getDistanceFee().add(this.getCourierPayout()));
    }

    public void place(){
        verifyCanBePlaced();
        this.setDeliveryStatus(DeliveryStatus.WAITING_FOR_COURIER);
        this.setPlacedAt(OffsetDateTime.now());

    }

    public void pickUP(UUID courierId){
        this.setCourierId(courierId);
        this.setDeliveryStatus(DeliveryStatus.IN_TRANSIT);
        this.setAssignedAt(OffsetDateTime.now());
    }

    public void markAsDeliviered(){
        this.setDeliveryStatus(DeliveryStatus.DELIVERED);
        this.setFulfilledAt(OffsetDateTime.now());
    }

    public void changeItemQuantity(UUID itemId,int quantity){
        Item item=getItems().stream().filter(i->i.getId().equals(itemId))
                .findFirst().orElseThrow();
        item.setQuantity(quantity);
        calculateTotalItens();
    }

    private void calculateTotalItens(){
       int totalItens= getItems().stream().mapToInt(Item::getQuantity).sum();
       setTotalItems(totalItens);
    }

    private void verifyCanBePlaced(){
        if(!isFilled()){
            throw new DomainException();
        }
        if(!getDeliveryStatus().equals(DeliveryStatus.DRAFT)){
            throw new DomainException();
        }
    }

    private boolean isFilled(){
        return this.getSender()!=null && this.getRecipient()!=null &&  this.getTotalCost()!=null;
    }

    private void verifyCanBeEdited(){
        if(!getDeliveryStatus().equals(DeliveryStatus.DRAFT)){
            throw new DomainException();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class PreparationDetails{
        private ContactPoint sender;
        private ContactPoint recipient;
        private BigDecimal distanceFee;
        private BigDecimal CourierPayout;
        private Duration expectedDeliveryTime;

    }




}

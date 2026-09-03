package com.dev.deliveryapp.domain.model;

import java.util.List;

public enum DeliveryStatus {
    DRAFT,
    WAITING_FOR_COURIER(DRAFT),
    IN_TRANSIT(WAITING_FOR_COURIER),
    DELIVERED(IN_TRANSIT);

    DeliveryStatus(DeliveryStatus... previousStatuses) {
        this.previousStatuses = List.of(previousStatuses);
    }

    private final List<DeliveryStatus> previousStatuses;

    public boolean canNotChangeTo(DeliveryStatus newStatus) {
        return !newStatus.previousStatuses.contains(this);
    }

    public boolean canChangeTo(DeliveryStatus newStatus){
        return !canNotChangeTo(newStatus);
    }


}

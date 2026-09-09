package com.dev.deliveryapp.domain.service;

import com.dev.deliveryapp.domain.model.Delivery;
import com.dev.deliveryapp.domain.model.excepetion.DomainException;
import com.dev.deliveryapp.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCheckpointService {
    private final DeliveryRepository deliveryRepository;

    //TODO extrai para um metodo o lançamento da exceção
    public void place(UUID deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException());
        delivery.place();
        deliveryRepository.saveAndFlush(delivery);
    }

    public void pickUp(UUID deliveryId,UUID courierId){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException());
        delivery.pickUP(courierId);
        deliveryRepository.saveAndFlush(delivery);
    }

    public void complete(UUID deliveryId){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() -> new DomainException());
        delivery.markAsDeliviered();
        deliveryRepository.saveAndFlush(delivery);
    }



}

package com.dev.deliveryapp.api.controller;

import com.dev.deliveryapp.api.model.CourierIdInput;
import com.dev.deliveryapp.api.model.DeliveryInput;
import com.dev.deliveryapp.domain.model.Delivery;
import com.dev.deliveryapp.domain.repository.DeliveryRepository;
import com.dev.deliveryapp.domain.service.DeliveryCheckpointService;
import com.dev.deliveryapp.domain.service.DeliveryPrepararionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryPrepararionService deliveryPrepararionService;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCheckpointService deliveryCheckpointService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Delivery draft(@RequestBody @Valid DeliveryInput input) {
        return deliveryPrepararionService.draft(input);
    }

    @PutMapping("/{deliveryId}")
    @ResponseStatus(HttpStatus.OK)
    public Delivery edit(@PathVariable UUID deliveryId,@RequestBody @Valid DeliveryInput input) {
        return deliveryPrepararionService.edit(deliveryId,input);
    }

    @GetMapping
    //TODO  inserir Dtos
    public PagedModel<Delivery>findAll(@PageableDefault Pageable pageable){
        return new PagedModel<>(deliveryRepository.findAll(pageable));
    }

    @GetMapping("/{deliveryId}")
    public Delivery findById(@PathVariable UUID deliveryId){
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{deliveryId}/placement")
    public void place(@PathVariable UUID deliveryId){
        deliveryCheckpointService.place(deliveryId);
    }

    @PostMapping("/{deliveryId}/pickups")
    public void pickup(@PathVariable UUID deliveryId,@Valid @RequestBody CourierIdInput courierInput){
        deliveryCheckpointService.pickUp(deliveryId,courierInput.getCourierId());
    }

    @PostMapping("/{deliveryId}/completion")
    public void complete(@PathVariable UUID deliveryId){
        deliveryCheckpointService.complete(deliveryId);
    }
}

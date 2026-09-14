package com.dev.deliveryapp.infra.event;

import com.dev.deliveryapp.domain.event.DeliveryFullFilleddEvent;
import com.dev.deliveryapp.domain.event.DeliveryPickUpEvent;
import com.dev.deliveryapp.domain.event.DeliveryPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.dev.deliveryapp.infra.kafka.KafkaTopicConfig.deliveryEventsTopicName;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeliveryDomainEventHandler {
    private final IntegrationEventPublisher integrationEventPublisher;

    @EventListener
    public void handle(DeliveryPlacedEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event,event.getDeliveryId().toString(),deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryPickUpEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event,event.getDeliveryId().toString(),deliveryEventsTopicName);
    }

    @EventListener
    public void handle(DeliveryFullFilleddEvent event){
        log.info(event.toString());
        integrationEventPublisher.publish(event,event.getDeliveryId().toString(),deliveryEventsTopicName);
    }
}

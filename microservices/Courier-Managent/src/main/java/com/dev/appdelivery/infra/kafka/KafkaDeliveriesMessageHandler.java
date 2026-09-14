package com.dev.appdelivery.infra.kafka;

import com.dev.appdelivery.infra.event.DeliveryFulFilledIntegrationEvent;
import com.dev.appdelivery.infra.event.DeliveryPlacedIntegrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(topics={"deliviries.v1.events"},groupId = "courier-managenmt")
@Slf4j
@RequiredArgsConstructor
public class KafkaDeliveriesMessageHandler {

    @KafkaHandler(isDefault = true)
    public void defaultHandler(@Payload Object object){
        log.info("Default Handler: {}",object);
    }
    @KafkaHandler
    public void handle(@Payload DeliveryPlacedIntegrationEvent event){
        log.info("Received: {}",event);
    }

    @KafkaHandler
    public void handle(@Payload DeliveryFulFilledIntegrationEvent event){
        log.info("Received: {}",event);
    }
}

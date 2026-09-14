package com.dev.deliveryapp.infra.event;

public interface IntegrationEventPublisher {
    void publish(Object event,String key,String topic);
}

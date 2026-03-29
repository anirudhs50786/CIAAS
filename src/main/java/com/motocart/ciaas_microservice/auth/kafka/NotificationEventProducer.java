package com.motocart.ciaas_microservice.auth.kafka;

import com.motocart.library.common.event.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public NotificationEventProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

        public void sendNotificationEvent(NotificationEvent event) {
        kafkaTemplate.send("notification-topic", event);
    }

}

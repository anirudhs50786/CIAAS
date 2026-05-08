package com.motocart.ciaas_microservice.kafka;

import com.motocart.library.common.event.NotificationEvent;
import com.motocart.library.kafka.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public NotificationEventProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

        public void sendNotificationEvent(NotificationEvent event) {
        kafkaTemplate.send(KafkaTopics.NOTIFICATION_EVENTS, event);
    }

}

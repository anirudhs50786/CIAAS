package com.motocart.ciaas_microservice.auth.service;

import com.motocart.ciaas_microservice.auth.entity.RoleEntity;
import com.motocart.ciaas_microservice.auth.entity.UserEntity;
import com.motocart.ciaas_microservice.kafka.NotificationEventProducer;
import com.motocart.library.common.event.NotificationEvent;
import com.motocart.library.common.types.NotificationType;
import com.motocart.library.common.types.Roles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationEventBuilder {

    @Value("${moto-cart.kafka.notification.enabled:false}")
    private boolean kafkaEnabled;


    private final NotificationEventProducer notificationEventProducer;

    public NotificationEventBuilder(NotificationEventProducer notificationEventProducer) {
        this.notificationEventProducer = notificationEventProducer;
    }

    /**
     * Build the Kafka notification event and send it asynchronously
     * @param user the user entity
     */
    @Async("ciaasExecutor")
    public void sendUserRegNotification(UserEntity user) {
        if (!kafkaEnabled) {
            return;
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("recipientName", user.getUsername());
        payload.put("loginLink", "dummy url");
        NotificationType type = user.getAuthorities().stream()
                .map(RoleEntity::getAuthority).
                anyMatch(s -> Roles.ROLE_ADMIN.name().equals(s)) ?
                NotificationType.ADMIN_REGISTRATION : NotificationType.USER_REGISTRATION;

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .notificationType(type)
                .recipientEmail(user.getEmail())
                .payload(payload)
                .build();
        notificationEventProducer.sendNotificationEvent(notificationEvent);
    }
}

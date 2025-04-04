package com.udara.beanwiring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final MessageService messageService;

    public NotificationService(@Qualifier("smsService") MessageService messageService) {
        log.info(">>> NotificationService Bean Instantiated - Injecting Dependency via Constructor: {}", messageService.getClass().getSimpleName());
        this.messageService = messageService;
    }

    public String sendNotification(String message) {
        log.info("NotificationService using injected {} to send message...", messageService.getClass().getSimpleName());
        return messageService.sendMessage(message);
    }
}

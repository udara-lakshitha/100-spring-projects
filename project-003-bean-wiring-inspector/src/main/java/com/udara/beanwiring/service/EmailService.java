package com.udara.beanwiring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
//@Primary
public class EmailService implements MessageService {
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public EmailService() {
        log.info(">>> EmailService Bean Instantiated by Spring Container!");
    }

    @Override
    public String sendMessage(String message) {
        String notification = "EMAIL notification " + message;
        log.info("Sending via EmailService {}", notification);
        return notification;
    }
}

package com.udara.beanwiring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService implements MessageService {

    private static final Logger log = LoggerFactory.getLogger(SmsService.class);

    public SmsService() {
        log.info(">>> SmsService Bean Instantiated by Spring Container!");
    }

    @Override
    public String sendMessage(String message) {
        String notification = "SMS notification " + message;
        log.info("Sending via SmsService {}", notification);
        return notification;
    }
}

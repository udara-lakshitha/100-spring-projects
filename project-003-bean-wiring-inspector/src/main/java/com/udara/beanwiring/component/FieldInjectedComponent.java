package com.udara.beanwiring.component;

import com.udara.beanwiring.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class FieldInjectedComponent {
    private static final Logger log = LoggerFactory.getLogger(FieldInjectedComponent.class);

    @Autowired
    @Qualifier("smsService")
    private MessageService messageService;

    public FieldInjectedComponent() {
        log.info(">>> FieldInjectedComponent Bean Instantiated (Dependency is NULL during construction: messageService={})", messageService);
    }

    public String useService(String message) {
        if (messageService == null) {
            log.info("!!! FieldComponent Error: MessageService was not injected before use!");
            return "Error: Service not available";
        }
        log.info("FieldInjectedComponent using injected {}...", messageService.getClass().getSimpleName());
        return "FieldComponent using " + messageService.getClass().getSimpleName() + ": " + messageService.sendMessage(message);
    }
}

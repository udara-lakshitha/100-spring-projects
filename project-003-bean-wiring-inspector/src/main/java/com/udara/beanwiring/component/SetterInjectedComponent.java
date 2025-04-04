package com.udara.beanwiring.component;

import com.udara.beanwiring.service.MessageService;
import com.udara.beanwiring.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SetterInjectedComponent {
    private static final Logger log = LoggerFactory.getLogger(SetterInjectedComponent.class);

    @Qualifier("emailService")
    private MessageService messageService;

    public SetterInjectedComponent() {
        log.info(">>> SetterInjectedComponent Bean Instantiated (Dependency is NULL initially)");
    }

    @Autowired
    public void setMesageService(@Qualifier("emailService") MessageService messageService) {
        log.info("--- SetterInjectedComponent: Injecting Dependency via Setter: {}", messageService.getClass().getSimpleName());
        this.messageService = messageService;
    }

    public String useService(String message) {
        if (messageService == null) {
            log.error("!!! SetterComponent Error: MessageService was not injected before use!");
            return "Error: Service not available";
        }
        log.info("SetterInjectedComponent using injected {}...", messageService.getClass().getSimpleName());
        return "SetterComponent using " + messageService.getClass().getSimpleName() + ": " + messageService.sendMessage(message);
    }
}

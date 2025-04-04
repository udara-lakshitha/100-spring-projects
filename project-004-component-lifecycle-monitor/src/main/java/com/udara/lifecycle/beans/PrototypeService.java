package com.udara.lifecycle.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrototypeService {

    private static final Logger log = LoggerFactory.getLogger(PrototypeService.class);
    private final String beanId;

    public PrototypeService() {
        this.beanId = "Prototype_" + System.nanoTime();
        log.info(">>> [{}]: Constructor", beanId);
    }

    @PostConstruct
    public void initPostConstruct() {
        log.info(">>> [{}]: @PostConstruct method", beanId);
    }

    public void doWork() {
        log.info("--- [{}]: Doing Prototype Work...", beanId);
    }

    @PreDestroy
    public void cleanupPreDestroy() {
        log.warn("<<< [{}]: @PreDestroy method - RARELY CALLED BY CONTAINER FOR PROTOTYPES!", beanId);
    }

    public String getBeanId() {
        return beanId;
    }
}

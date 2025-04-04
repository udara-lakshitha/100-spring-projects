package com.udara.lifecycle.beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SingletonService implements InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(SingletonService.class);
    private final String beanId;

    public SingletonService() {
        this.beanId = "Singleton_" + System.nanoTime();
        log.info(">>> [{}]: Constructor", beanId);
    }

    @PostConstruct
    public void initPostConstruct() {
        log.info(">>> [{}]: @PostConstruct method", beanId);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>> [{}]: afterPropertiesSet() [InitializingBean]", beanId);
    }

    public void doWork() {
        log.info("--- [{}]: Doing Singleton Work...", beanId);
    }

    @PreDestroy
    public void cleanupPreDestroy() {
        log.info("<<< [{}]: @PreDestroy method", beanId);
    }

    public String getBeanId() {
        return beanId;
    }

    @Override
    public void destroy() throws Exception {
        log.info("<<< [{}]: destroy() [DisposableBean]", beanId);
    }
}

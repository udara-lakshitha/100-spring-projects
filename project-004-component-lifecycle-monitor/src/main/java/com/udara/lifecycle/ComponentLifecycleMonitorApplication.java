package com.udara.lifecycle;

import com.udara.lifecycle.beans.PrototypeService;
import com.udara.lifecycle.beans.SingletonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ComponentLifecycleMonitorApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ComponentLifecycleMonitorApplication.class);
	private final ApplicationContext applicationContext;

	public ComponentLifecycleMonitorApplication(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public static void main(String[] args) {
		SpringApplication.run(ComponentLifecycleMonitorApplication.class, args);
		log.info("------ APPLICATION CONTEXT CLOSED ------");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("------ CommandLineRunner STARTING ------");
		log.info("------ Demonstrating SINGLETON Scope ------");

		SingletonService singleton1 = applicationContext.getBean(SingletonService.class);
		log.info("Retrieved Singleton Bean 1 - ID: {}", singleton1.getBeanId());
		singleton1.doWork();

		SingletonService singleton2 = applicationContext.getBean(SingletonService.class);
		log.info("Retrieved Singleton Bean 2 - ID: {}", singleton2.getBeanId());
		singleton2.doWork();

		log.info("Are Singleton instances the same object? ---> {}", (singleton1 == singleton2));

		log.info("------ SINGLETON Scope Demo Complete ------\n");

		log.info("------ Demonstrating PROTOTYPE Scope ------");

		PrototypeService prototype1 = applicationContext.getBean(PrototypeService.class);
		log.info("Retrieved Prototype Bean 1 - ID: {}", prototype1.getBeanId());
		prototype1.doWork();

		PrototypeService prototype2 = applicationContext.getBean(PrototypeService.class);
		log.info("Retrieved Prototype Bean 2 - ID: {}", prototype2.getBeanId());
		prototype2.doWork();

		PrototypeService prototype3 = applicationContext.getBean(PrototypeService.class);
		log.info("Retrieved Prototype Bean 3 - ID: {}", prototype3.getBeanId());
		prototype3.doWork();

		log.info("Are Prototype instances (1 vs 2) the same object? ---> {}", (prototype1 == prototype2));

		log.info("------ PROTOTYPE Scope Demo Complete ------");
		log.info("------ Observe Bean Creation Logs Above (Constructor, @PostConstruct, InitializingBean) ------");
		log.info("------ Observe Bean Destruction Logs on Shutdown (@PreDestroy, DisposableBean for SINGLETON ONLY) ------");
		log.info("------ Note the likely ABSENCE of @PreDestroy logs for Prototype beans! ------");
		log.info("------ CommandLineRunner FINISHED ------");
	}
}

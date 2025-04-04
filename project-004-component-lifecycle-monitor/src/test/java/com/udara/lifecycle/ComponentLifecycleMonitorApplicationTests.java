package com.udara.lifecycle;

import com.udara.lifecycle.beans.PrototypeService;
import com.udara.lifecycle.beans.SingletonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ComponentLifecycleMonitorApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(ComponentLifecycleMonitorApplicationTests.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	@DisplayName("Singleton scope should return the same bean instance")
	public void testSingletonScope() {
		log.info("--- Test: Requesting Singleton bean twice ---");
		SingletonService instance1 = applicationContext.getBean(SingletonService.class);
		SingletonService instance2 = applicationContext.getBean(SingletonService.class);

		log.info("Singleton Instance 1 ID: {}", instance1.getBeanId());
		log.info("Singleton Instance 2 ID: {}", instance2.getBeanId());

		assertThat(instance1)
				.isNotNull()
				.isSameAs(instance2);
		log.info("--- Test: Singleton instances verified to be the same object.");
	}

	@Test
	@DisplayName("Prototype scope should return a new bean instance each time")
	public void testPrototypeScope() {
		log.info("--- Test: Requesting Prototype bean multiple times ---");
		PrototypeService instance1 = applicationContext.getBean(PrototypeService.class);
		PrototypeService instance2 = applicationContext.getBean(PrototypeService.class);
		PrototypeService instance3 = applicationContext.getBean(PrototypeService.class);

		log.info("Prototype Instance 1 ID: {}", instance1.getBeanId());
		log.info("Prototype Instance 2 ID: {}", instance2.getBeanId());
		log.info("Prototype Instance 3 ID: {}", instance3.getBeanId());

		assertThat(instance1).isNotNull();
		assertThat(instance2).isNotNull();
		assertThat(instance3).isNotNull();

		assertThat(instance1).isNotSameAs(instance2);
		assertThat(instance1).isNotSameAs(instance3);
		assertThat(instance2).isNotSameAs(instance3);

		log.info("--- Test: Prototype instances verified to be different objects.");
	}

	@Test
	void contextLoads() {
	}

}

package com.udara.beanwiring;

import com.udara.beanwiring.Controller.WiringController;
import com.udara.beanwiring.component.FieldInjectedComponent;
import com.udara.beanwiring.component.SetterInjectedComponent;
import com.udara.beanwiring.service.EmailService;
import com.udara.beanwiring.service.MessageService;
import com.udara.beanwiring.service.NotificationService;
import com.udara.beanwiring.service.SmsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DependencyInjectionTheoryTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private WiringController wiringController;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private FieldInjectedComponent fieldInjectedComponent;

    @Autowired
    private SetterInjectedComponent setterInjectedComponent;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Test
    @DisplayName("Spring ApplicationContext should load successfully")
    public void contextLoads() {
        assertThat(context).isNotNull();
        System.out.println("ApplicationContext loaded successfully");
    }

    @Test
    @DisplayName("All required beans should be present in the context")
    public void requiredBeansArePresented() {
        assertThat(wiringController).isNotNull();
        assertThat(notificationService).isNotNull();
        assertThat(setterInjectedComponent).isNotNull();
        assertThat(fieldInjectedComponent).isNotNull();
        assertThat(emailService).isNotNull();
        assertThat(smsService).isNotNull();
        System.out.println("All core beans are verified");
    }

    @Test
    @DisplayName("Constructor Injection in NotificationService should use specified Qualifier")
    public void constructorInjectionQualifier() {
        String result = notificationService.sendNotification("Test");
        assertThat(result).startsWith("SMS notification");
        System.out.println("Verified NotificationService (Constructor Injection) uses SmsService via @Qualifier.");
    }

    @Test
    @DisplayName("Setter Injection in SetterComponent should use specified Qualifier")
    public void setterInjectionQualifier() {
        String result = setterInjectedComponent.useService("Test");
        assertThat(result).contains("EmailService").startsWith("SetterComponent");
        System.out.println("Verified SetterInjectedComponent uses EmailService via @Qualifier.");
    }

    @Test
    @DisplayName("Field Injection in FieldComponent should use specified Qualifier")
    public void fieldInjectionQualifier() {
        String result = fieldInjectedComponent.useService("Test");
        assertThat(result).contains("SmsService").startsWith("FieldComponent");
        System.out.println("Verified FieldInjectedComponent uses SmsService via @Qualifier.");
    }

    @Test
    @DisplayName("Can retrieve specific beans by name/qualifier from context")
    void retrieveBeansByName() {
        MessageService email = (MessageService) context.getBean("emailService");
        MessageService sms = (MessageService) context.getBean("smsService");
        assertThat(email).isInstanceOf(EmailService.class);
        assertThat(sms).isInstanceOf(SmsService.class);
        System.out.println("Verified retrieval of beans by default names.");
    }
}

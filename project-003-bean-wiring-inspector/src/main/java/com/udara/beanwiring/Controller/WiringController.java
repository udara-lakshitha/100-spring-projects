package com.udara.beanwiring.Controller;

import com.udara.beanwiring.component.FieldInjectedComponent;
import com.udara.beanwiring.component.SetterInjectedComponent;
import com.udara.beanwiring.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WiringController {

    private final NotificationService constructorInjectorService;
    private final FieldInjectedComponent fieldInjectedComponent;
    private final SetterInjectedComponent setterInjectedComponent;

    public WiringController(
            NotificationService constructorInjectorService,
            FieldInjectedComponent fieldInjectedComponent,
            SetterInjectedComponent setterInjectedComponent
    ) {
        this.constructorInjectorService = constructorInjectorService;
        this.fieldInjectedComponent=  fieldInjectedComponent;
        this.setterInjectedComponent = setterInjectedComponent;
    }

    @GetMapping("/notify-constructor")
    public String notifyConstructor(@RequestParam(defaultValue = "Constructor Test") String msg) {
        return "Via constructor injector -> " + constructorInjectorService.sendNotification(msg);
    }

    @GetMapping("/notify-setter")
    public String notifySetter(@RequestParam(defaultValue = "Setter Test") String msg) {
        return "Via setter injector -> " + setterInjectedComponent.useService(msg);
    }

    @GetMapping("/notify-field")
    public String notifyField(@RequestParam(defaultValue = "Field Test") String msg) {
        return "Via Field injector -> " + fieldInjectedComponent.useService(msg);
    }

    @GetMapping("/")
    public String home() {
        return "Bean Wiring Inspector: Demonstrating Dependency Injection!";
    }
}

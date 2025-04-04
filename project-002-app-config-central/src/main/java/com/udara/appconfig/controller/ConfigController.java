package com.udara.appconfig.controller;

import com.udara.appconfig.config.AppDetailsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConfigController {
    @Value("${app.greeting}")
    private String greetingMessage;

    @Value("${app.feature.enabled}")
    private boolean featureEnabled;

    private final AppDetailsConfig appDetailsConfig;
    private final Environment environment;

    @Autowired
    public ConfigController(AppDetailsConfig appDetailsConfig, Environment environment) {
        this.appDetailsConfig = appDetailsConfig;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("greeting", greetingMessage);
        configMap.put("featureEnabled", featureEnabled);
        configMap.put("appDetails", appDetailsConfig);
        configMap.put("databaseUrlFromEnv", environment.getProperty("app.database.url"));
        configMap.put("databaseUserFromEnv", environment.getProperty("app.database.user"));
        configMap.put("databasePasswordFromEnv (Hidden)", environment.getProperty("app.database.password", "NOT SET"));
        configMap.put("activeProfiles", environment.getActiveProfiles());
        return configMap;
    }

    @GetMapping("/")
    public String home() {
        return "App Config Central is running! Active profiles: " + String.join(", ", environment.getActiveProfiles());
    }
}

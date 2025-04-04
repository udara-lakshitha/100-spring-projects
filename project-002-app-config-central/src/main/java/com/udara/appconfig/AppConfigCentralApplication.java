package com.udara.appconfig;

import com.udara.appconfig.config.AppDetailsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppDetailsConfig.class)
public class AppConfigCentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppConfigCentralApplication.class, args);
	}

}

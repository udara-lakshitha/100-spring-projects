# Project 002: App Config Central

## Purpose
This project explores Spring Boot's configuration management capabilities. It demonstrates how to load properties from different files (`.properties`), use Spring Profiles to manage environment-specific configurations (`dev`, `prod`), and access configuration values using both `@Value` and type-safe `@ConfigurationProperties`. It also shows the property overriding behavior.

## Key Concepts Demonstrated
*   `application.properties` (default configuration)
*   Profile-specific properties (`application-{profile}.properties`)
*   Activating profiles (`spring.profiles.active` via ENV var or JVM arg)
*   Injecting properties using `@Value`
*   Type-safe configuration binding with `@ConfigurationProperties`
*   Property source ordering and overriding (ENV > profile > default)
*   Using `Environment` bean to inspect properties and profiles
*   Testing configuration with `@SpringBootTest` and `@ActiveProfiles`

## Technologies Used
*   Java 17
*   Spring Boot 3.2.0
*   Spring Web
*   Spring Boot DevTools (Optional)
*   Spring Configuration Processor (Optional)
*   Spring Boot Starter Test (JUnit 5, AssertJ, `@SpringBootTest`)
*   Maven 3.x (or Gradle)

## Setup & Run
1.  **Prerequisites:** JDK 17+, Maven 3.x (or Gradle) installed.
2.  **Generate Project:** This project was generated using Spring Initializr ([start.spring.io](https://start.spring.io/)) with `Spring Web`, `DevTools`, and `Configuration Processor` dependencies.
3.  **Navigate:** `cd 100-spring-projects/project-002-app-config-central`
4.  **Run (Default Profile):**
    ```bash
    mvn spring-boot:run
    # or ./gradlew bootRun
    ```
5.  **Run (Dev Profile - via JVM arg):**
    ```bash
    mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
    # or ./gradlew bootRun --args='--spring.profiles.active=dev'
    ```
6.  **Run (Prod Profile - via ENV Var - Linux/macOS):**
    ```bash
    export SPRING_PROFILES_ACTIVE=prod
    mvn spring-boot:run # or ./gradlew bootRun
    unset SPRING_PROFILES_ACTIVE # Cleanup
    ```
*(See previous response for Windows ENV VAR commands)*
7. **Access:** Check `http://localhost:8080/config` in your browser to see the loaded configuration for the active profile. Check `http://localhost:8080/` to see the active profile name.

## Testing Strategy
*   **Integration Tests:** Tests use `@SpringBootTest` to load the application context.
*   **Profile Testing:** The `@ActiveProfiles` annotation is used on test classes to activate specific profiles (`dev`, `prod`) and verify that the correct configuration values are loaded.
*   **Run Tests:**
    ```bash
    mvn test
    # or ./gradlew test
    ```

## API Documentation

| Method | Path      | Description                                       | Example Response (JSON)                     |
| :----- | :-------- | :------------------------------------------------ | :------------------------------------------ |
| GET    | `/`       | Returns a status message with active profiles.    | `App Config Central is running! Active profiles: dev` |
| GET    | `/config` | Returns a map of loaded configuration values.     | `{ "greeting": "...", "featureEnabled": ..., "appDetails": { ... }, ...}` |

## Deployment Notes
*   **Local:** Use `mvn spring-boot:run` or `./gradlew bootRun` with appropriate profile activation method (JVM arg or ENV var).
*   **PaaS (Fly.io, Render, etc.):** Deploy the application JAR. Set the active profile using environment variables provided by the PaaS platform (e.g., `SPRING_PROFILES_ACTIVE=prod`). Use platform secrets/environment variables for sensitive data.
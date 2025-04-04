# Project 003: Bean Wiring Inspector - Understanding Dependency Injection

## Purpose
This project provides a practical demonstration and theoretical explanation of Spring Boot's core **Inversion of Control (IoC)** and **Dependency Injection (DI)** mechanisms. It explores how Spring manages beans (objects) and injects dependencies using Constructor, Setter, and Field injection, highlighting best practices. It crucially demonstrates how to handle ambiguity when multiple beans of the same type could satisfy a dependency, using `@Primary` and `@Qualifier`.

## Core Theory Explained

*   **Inversion of Control (IoC):** Instead of objects creating their own dependencies (like `new EmailService()`), control is inverted. The **Spring IoC Container (Application Context)** is responsible for creating, configuring, wiring, and managing the lifecycle of objects (Beans). Objects simply declare their dependencies. *Benefit:* Loose coupling, modularity, testability.
*   **Dependency Injection (DI):** The *process* by which the IoC Container provides the dependencies to a bean.
*   **Spring Beans:** Objects managed by the Spring Container, typically annotated with `@Component`, `@Service`, `@Repository`, `@Controller`, or defined via `@Bean`.
*   **Component Scan:** How Spring finds classes annotated with stereotype annotations to create beans (usually scans the main application package and sub-packages).

## Key Concepts Demonstrated

*   **IoC Container & Beans:** Creating beans using `@Service`, `@Component`. Logging shows when Spring instantiates them.
*   **Component Scanning:** Implicitly used by `@SpringBootApplication`.
*   **Dependency Injection Types:**
    *   **Constructor Injection:** (See `NotificationService`, `WiringController`) Dependencies passed via constructor. **Preferred method** because it ensures required dependencies are available upon construction, supports immutability (`final` fields), and makes dependencies explicit. `@Autowired` is optional if only one constructor exists.
    *   **Setter Injection:** (See `SetterInjectedComponent`) Dependencies injected via public `@Autowired` setter methods *after* construction. Use for optional dependencies or (carefully) resolving circular dependencies. Makes testing slightly harder than constructor injection.
    *   **Field Injection:** (See `FieldInjectedComponent`) Dependencies injected directly into fields using `@Autowired` via reflection *after* construction. **Strongly discouraged** for application components because it hides dependencies, makes unit testing difficult (requires reflection or Spring context), and can violate design principles.
*   **Ambiguity Problem:** Demonstrated by the initial application startup failure when multiple `MessageService` beans (`EmailService`, `SmsService`) exist and Spring doesn't know which one to inject into `NotificationService` by default.
*   **Ambiguity Resolution:**
    *   **`@Primary`:** (Demonstrated on `EmailService`) Marks one bean implementation as the default candidate if multiple beans of the same type exist. Used when one implementation is the most common choice.
    *   **`@Qualifier("beanName")`:** (Demonstrated in `NotificationService`, `SetterInjectedComponent`, `FieldInjectedComponent`) Used at the injection point (constructor arg, setter arg, field) to specify the exact name of the bean to inject. Bean names default to the unqualified class name with the first letter lowercased (e.g., `emailService`, `smsService`). Provides explicit control.

## Technologies Used
*   Java 17
*   Spring Boot 3.2.0
*   Spring Web
*   Spring Boot DevTools (Optional)
*   Spring Boot Starter Test (JUnit 5, AssertJ, `@SpringBootTest`)
*   Maven 3.x (or Gradle)
*   SLF4J (for logging)

## Setup & Run
1.  **Prerequisites:** JDK 17+, Maven 3.x (or Gradle).
2.  **Generate:** Project created via Spring Initializr with `Spring Web`.
3.  **Navigate:** `cd 100-spring-projects/project-003-bean-wiring-inspector`
4.  **Configure Ambiguity Resolution:** Ensure **one** resolution strategy is active for `NotificationService` for consistent runs/tests (the code is currently set up for `@Qualifier("smsService")` in `NotificationService` constructor, `@Qualifier("emailService")` in `SetterInjectedComponent`, and `@Qualifier("smsService")` in `FieldInjectedComponent`).
5.  **Run:**
    ```bash
    mvn spring-boot:run
    # or ./gradlew bootRun
    ```
6.  **Access Endpoints (Examples):**
    *   `http://localhost:8080/`
    *   `http://localhost:8080/notify-constructor?msg=Hello`
    *   `http://localhost:8080/notify-setter?msg=Hi`
    *   `http://localhost:8080/notify-field?msg=Hey`
    *   *Observe console logs to see which beans are created and which services are used.*

## Testing Strategy
*   **Integration Tests:** `DependencyInjectionTheoryTest.java` uses `@SpringBootTest` to load the full Application Context.
*   **Verification:** Tests confirm the context loads, required beans exist, and demonstrate that the injection strategies (including ambiguity resolution via `@Qualifier` as configured) are working correctly by checking the output messages.
*   **Run Tests:**
    ```bash
    mvn test
    # or ./gradlew test
    ```

## Deployment Notes
*   Runs locally. Deployable to any PaaS (Fly.io, Render) without external service dependencies.
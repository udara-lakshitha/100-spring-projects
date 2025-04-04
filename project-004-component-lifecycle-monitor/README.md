# Project 004: Component Lifecycle Monitor

## Purpose
This project explores how the Spring IoC Container manages the lifecycle of its beans. It focuses on contrasting **Bean Scopes** (`Singleton` vs. `Prototype`) and demonstrates the different **Lifecycle Callback** mechanisms available (`InitializingBean`, `DisposableBean`, `@PostConstruct`, `@PreDestroy`).

## Core Theory Explained

*   **Bean Scopes:** Determine how many instances of a bean are created and managed by the Spring container.
    *   **`Singleton` (Default):** Only *one* shared instance is created per container. Every request for the bean returns this same instance. Spring manages its complete lifecycle, including creation and destruction callbacks. Use for stateless services or objects where shared state is intended and thread-safe.
    *   **`Prototype`:** A *new* instance is created *every time* the bean is requested (`getBean()` or injected into another prototype). Spring initializes the bean (calls `@PostConstruct`, `InitializingBean`) but then hands it off. **Crucially, Spring does *not* manage the destruction of prototype beans**; `@PreDestroy` / `DisposableBean.destroy()` are generally *not* called automatically by the container. Use for stateful objects where each client needs its own independent instance.
*   **Lifecycle Callbacks:** Hooks allowing custom logic execution at specific points in a bean's lifecycle.
    *   **Initialization:**
        *   `@PostConstruct`: Annotation on a method. Called *after* dependency injection is complete. **Preferred modern approach.**
        *   `InitializingBean`: Interface with `afterPropertiesSet()` method. Called *after* `@PostConstruct`. Older approach.
    *   **Destruction (Primarily for Singletons):**
        *   `@PreDestroy`: Annotation on a method. Called *before* the bean is destroyed during container shutdown. **Preferred modern approach.**
        *   `DisposableBean`: Interface with `destroy()` method. Called *after* `@PreDestroy`. Older approach.

## Key Concepts Demonstrated

*   **Bean Scopes:** Defining `@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)` vs. the default Singleton scope.
*   **Lifecycle Callbacks:** Implementing `InitializingBean`, `DisposableBean` and using `@PostConstruct`, `@PreDestroy`.
*   **Instantiation Differences:** Observing via logs that Singleton constructor runs once, Prototype constructor runs on each request.
*   **Lifecycle Callback Order:** Observing the sequence of initialization/destruction logs.
*   **Prototype Destruction Caveat:** Highlighting (via logs and documentation) that `@PreDestroy`/`destroy` are typically not called by the container for prototypes.
*   **ApplicationContext:** Retrieving beans programmatically using `getBean()`.
*   **CommandLineRunner:** Executing logic after the context is fully initialized.
*   **Testing Scopes:** Verifying Singleton returns the same instance and Prototype returns new instances using `@SpringBootTest`.

## Technologies Used
*   Java 17
*   Spring Boot 3.2.0
*   Spring Web (for context runner)
*   Spring Boot Starter Test (JUnit 5, AssertJ, `@SpringBootTest`)
*   Maven 3.x (or Gradle)
*   SLF4J / Logback (for detailed logging)

## Setup & Run
1.  **Prerequisites:** JDK 17+, Maven 3.x (or Gradle).
2.  **Generate:** Project created via Spring Initializr with `Spring Web`.
3.  **Navigate:** `cd 100-spring-projects/project-004-component-lifecycle-monitor`
4.  **Run:**
    ```bash
    mvn spring-boot:run
    # or ./gradlew bootRun
    ```
5.  **Observe Console Output:** This project's primary output is the console log. Carefully watch the sequence of messages during:
    *   **Startup:** Singleton bean initialization logs.
    *   **Runtime (CommandLineRunner):** Logs showing bean retrieval, Singleton vs. Prototype instance IDs/comparisons, and **multiple** Prototype initialization logs.
    *   **Shutdown (after CTRL+C):** Singleton destruction logs (`@PreDestroy`, `destroy`). Note the expected *absence* of Prototype destruction logs.

## Testing Strategy
*   **Integration Tests:** `BeanScopeLifecycleTest.java` uses `@SpringBootTest` to load the context.
*   **Verification:** Tests programmatically retrieve beans from the context and use AssertJ assertions (`isSameAs`, `isNotSameAs`) to verify that `SingletonService` returns the same instance while `PrototypeService` returns new instances on each call to `getBean()`. Lifecycle callback *sequence* is primarily verified by observing logs during manual runs.
*   **Run Tests:**
    ```bash
    mvn test
    # or ./gradlew test
    ```

## Deployment Notes
*   This is a demonstration application primarily focused on console output during local execution.
*   It can be run locally using `mvn spring-boot:run` or packaged into a JAR. Deployment to a PaaS isn't the main goal but is possible.
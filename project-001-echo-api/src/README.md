# Project 001: Echo Chamber API

## Purpose
This is the first project in the 100 Spring Boot projects series. It serves as a basic "Hello World" for Spring Boot web applications, demonstrating the minimal setup required to create a working REST endpoint. The API simply echoes back a message provided as a query parameter.

## Key Concepts Demonstrated
*   Basic Spring Boot Project Setup (Maven)
*   `@SpringBootApplication`
*   `@RestController`
*   `@GetMapping`
*   `@RequestParam` (with default value)
*   Running embedded Tomcat server
*   Basic Controller Testing (`@WebMvcTest`, `MockMvc`)

## Technologies Used
*   Java 17
*   Spring Boot 3.2.0
*   Spring Web
*   Spring Boot Starter Test (JUnit 5, Mockito, MockMvc)
*   Maven 3.x

## Setup & Run
1.  **Prerequisites:** JDK 17+, Maven 3.x installed.
2.  **Clone the main repository:** (If you haven't already)
    ```bash
    git clone <your-repo-url>
    cd 100-spring-projects/project-001-echo-api
    ```
3.  **Build (Optional):** Maven automatically downloads dependencies when running. To build explicitly:
    ```bash
    mvn clean package
    ```
4.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

## Testing Strategy
*   **Unit/Integration Tests:** Basic controller tests are implemented using `@WebMvcTest` and `MockMvc`. This tests the web layer in isolation.
*   **Run Tests:** Execute the tests using Maven:
    ```bash
    mvn test
    ```
*   **Manual Testing:** Access the endpoints via browser or curl:
    *   `http://localhost:8080/`
    *   `http://localhost:8080/echo`
    *   `http://localhost:8080/echo?message=YourMessageHere`

## API Documentation

| Method | Path         | Query Parameter        | Description                                   | Example Response        |
| :----- | :----------- | :--------------------- | :-------------------------------------------- | :---------------------- |
| GET    | `/`          | -                      | Returns a simple status message.              | `Echo Chamber API is running!` |
| GET    | `/echo`      | `message` (Optional)   | Echoes the provided message or "Hello".     | `Echo: <message>`       |

## Deployment Notes
*   **Local:** Runs directly using `mvn spring-boot:run`.
*   **PaaS (Platform as a Service - e.g., Fly.io, Render):** This simple application can typically be deployed directly using built-in buildpacks on platforms like Fly.io or Render, which detect Java/Spring Boot projects. (Specific deployment steps would be covered in later projects).
# Project 005: Volatile Notes API

## Purpose
This project implements a simple RESTful API for managing text notes (Create, Read, Update, Delete - CRUD). It demonstrates fundamental REST API design principles using Spring Boot. The data is stored **in-memory** only while the application is running, making it "volatile".

## Key Concepts Demonstrated
*   **RESTful CRUD Operations:** Mapping HTTP methods (POST, GET, PUT, DELETE) to business logic.
*   **Service Layer:** Separating data manipulation logic (`InMemoryNoteService`) from the web controller (`NoteController`).
*   **In-Memory Data Storage:** Using `ConcurrentHashMap` for basic, non-persistent data storage during application runtime.
*   **Path Variables (`@PathVariable`):** Identifying specific resources in URLs (e.g., `/notes/{id}`).
*   **Request Body (`@RequestBody`):** Receiving JSON data in POST/PUT requests and mapping it to DTOs (`CreateNoteDto`).
*   **DTOs (Data Transfer Objects):** Using `CreateNoteDto` for request data and `Note` model for response data.
*   **ResponseEntity:** Constructing HTTP responses with specific status codes (200 OK, 201 Created, 204 No Content).
*   **Exception Handling (`@RestControllerAdvice`, `@ExceptionHandler`):** Gracefully handling errors like "Note Not Found" and returning appropriate status codes (404 Not Found).
*   **Testing Controllers (`@WebMvcTest`, `MockMvc`):** Isolating the controller layer for testing.
*   **Mocking Dependencies (`@MockBean`):** Replacing the actual service with a mock during controller tests using Mockito.
*   **JSON Testing:** Using `ObjectMapper` and `jsonPath` to verify JSON request bodies and response content in tests.

## Technologies Used
*   Java 17
*   Spring Boot 3.2.0
*   Spring Web
*   Spring Boot Starter Test (JUnit 5, Mockito, MockMvc, AssertJ, Jackson)
*   Maven 3.x (or Gradle)
*   SLF4J / Logback

## Setup & Run
1.  **Prerequisites:** JDK 17+, Maven 3.x (or Gradle).
2.  **Generate:** Project created via Spring Initializr with `Spring Web`.
3.  **Navigate:** `cd 100-spring-projects/project-005-volatile-notes-api`
4.  **Run:**
    ```bash
    mvn spring-boot:run
    # or ./gradlew bootRun
    ```
    The API will be available at `http://localhost:8080/notes`.

## API Endpoints

| Method | Path         | Request Body        | Description                       | Success Status | Failure Status |
| :----- | :----------- | :------------------ | :-------------------------------- | :------------- | :------------- |
| POST   | `/notes`     | `CreateNoteDto` JSON | Creates a new note.               | 201 Created    | 400 Bad Request (Implicit if JSON invalid) |
| GET    | `/notes`     | -                   | Retrieves all notes.              | 200 OK         | -              |
| GET    | `/notes/{id}`| -                   | Retrieves a single note by ID.    | 200 OK         | 404 Not Found  |
| PUT    | `/notes/{id}`| `CreateNoteDto` JSON | Updates an existing note by ID.   | 200 OK         | 404 Not Found  |
| DELETE | `/notes/{id}`| -                   | Deletes a note by ID.             | 204 No Content | 404 Not Found  |

**`CreateNoteDto` JSON Example:**
```json
{
  "content": "This is the note content."
}
```
**`Note` JSON Example:**
```json
{
  "id": 1,
  "content": "This is the note content."
}
```
## Testing Strategy
*   **Run Tests:**
    ```bash
    mvn test
    # or ./gradlew test
    ```

## Deployment Notes
*   It can be run locally using `mvn spring-boot:run` or packaged into a JAR. Deployment to a PaaS isn't the main goal but is possible.
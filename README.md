## How to run

1. Start Kafka locally (docker-compose -f docker-compose.yml up -d)
2. Run the application:
   `./mvnw spring-boot:run`
3. Use the /events/status endpoint to set events as "live" or "not live".

## Endpoints

- `POST /events/status`
  - Example payload:
    ```
    {
      "eventId": "1234",
      "status": "LIVE"
    }
    ```

## Swagger

http://localhost:8080/swagger-ui/index.html
https://live-events-tracker-production.up.railway.app/swagger-ui/index.html#/

## Tests

```
./mvnw test
```

## Decisões de design

- Spring Boot 3.5 + Java 21: Uses modern versions for better performance, features, and compatibility.
- Modular Structure: Clear separation between controllers, services, scheduler, and Kafka publisher.
- Dependency Injection with Lombok (@RequiredArgsConstructor): Promotes immutability and testability.
- In-memory storage: Event status (LIVE/NOT_LIVE) is maintained in a simple ConcurrentHashMap for prototyping.
- Scheduling: Polls live events every 10 seconds using @Scheduled.
- The application currently uses a manual retry mechanism. For greater robustness and flexibility, integrating Resilience4j is recommended.
- Testing: Coverage of core flows, with mocks for external dependencies (Kafka, APIs).
- Testing: Coverage of core flows, with mocks for external dependencies (Kafka, APIs).


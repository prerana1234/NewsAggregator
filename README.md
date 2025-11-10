
# News Aggregator (Spring Boot + simple frontend)

This repo contains a Spring Boot backend and a tiny frontend shipped in `src/main/resources/static` so you can run both together.

## How to run
- Build with Maven (Java 17):
  ```bash
  mvn clean package
  java -jar target/news-aggregator-0.1.0.jar
  ```
- Or run from your IDE (main class `com.example.newsaggregator.NewsAggregatorApplication`).

The backend listens on port 8080. The frontend is available at `http://localhost:8080/` and calls `/api/news` endpoint.

## Notes
- Guardian and NYT API adapters are present. If you set environment variables `GUARDIAN_API_KEY` and `NYT_API_KEY` the clients will attempt to call the real APIs (parsing not implemented in this scaffold).
- If API keys are not present, clients return sample data so the service remains available (offline-friendly).
- OpenAPI (Swagger UI) available at `/swagger-ui.html` (springdoc).
- Dockerfile and Jenkinsfile included.


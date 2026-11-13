# Eco-Travel & Adventure Management System

A distributed platform for managing eco-friendly travel: accommodation, maintenance, bookings, finance, guides and adventures. It is built as a set of **Spring Boot microservices** that communicate over REST, with an API gateway, centralized configuration and fault tolerance.

> Team project for the *System Programming* course at the School of Computing (RAF), Union University. Built by a team of three.

---

## Architecture

```
                    ┌──────────────────┐
   Client ────────▶ │  Gateway :8080   │  routing, authentication, rate limiting
                    └────────┬─────────┘
                             │  X-Authenticated-User / X-User-Role headers
   ┌──────────┬──────────────┼──────────────┬──────────┬──────────┐
   ▼          ▼              ▼              ▼          ▼          ▼
 Auth    Accommodation   Maintenance     Booking    Finance   Adventures / Guides
 :8079      :8081          :8082          :8090      :8091     :8083 / :8084
                  ▲            │             │
                  └── OpenFeign + Circuit Breaker / Retry ──┘

             All services load their configuration from Config Server :8888
```

| Microservice | Responsibility | Port |
|:--|:--|:--|
| **Config Server** | Centralized configuration for all services | `8888` |
| **Gateway Service** | Single entry point, routing, security, rate limiting | `8080` |
| **Auth Service** | Authentication and user roles | `8079` |
| **Accommodation Service** | Accommodation units, addresses, pricing, availability calendar, search & analytics | `8081` |
| **Maintenance Service** | Workers, maintenance tasks and work orders for units | `8082` |
| **Avanture Service** | Adventure orchestration (Facade & Factory patterns) | `8083` |
| **Vodici Service** | Guide profiles, languages and certifications | `8084` |
| **Booking Service** | Reservations and price calculation | `8090` |
| **Finance Service** | Invoicing, taxes and transactions | `8091` |

## Key features

- **API Gateway & security**: one entry point that authenticates requests and passes user identity and role to internal services through headers. Supports multiple roles and per-service permissions.
- **Inter-service communication**: declarative REST clients with **OpenFeign**. Custom `ErrorDecoder`s map downstream errors (for example a 404) to proper business exceptions.
- **Resilience**: **Circuit Breaker** and **Retry** (Resilience4j) on critical calls, such as Booking → Accommodation and Maintenance → Accommodation. If a service is down, callers get a clear `503 Service Unavailable` instead of cascading failures.
- **Centralized configuration**: services fetch their settings from the Config Server, so configuration changes don't require rebuilding.
- **Uniform error handling**: a global exception handler in each service returns a consistent error response format.
- **Observability**: request tracing with a trace ID propagated through logs (MDC).
- **API documentation**: Swagger UI (SpringDoc OpenAPI) for every service.

## My contribution (Nikola Vasiljević)

- Set up the initial **Spring Boot multi-module project** skeleton and Maven configuration.
- Designed and implemented the **Accommodation Service**:
  - REST API for units, addresses, facilities, price tiers and the availability calendar
  - **Factory pattern** (`UnitFactoryRegistry` with apartment, cabin and house factories) for creating different unit types
  - Search and analytics endpoints (`/api/analytics/search`, `/average-price`, `/by-facility`)
- Designed and implemented the **Maintenance Service**: workers, maintenance tasks, work orders and maintenance logs.
- Integrated **Maintenance → Accommodation** with an OpenFeign client behind a **Facade** (`AccommodationClientFacade`), protected with Circuit Breaker and Retry, with fallbacks that tell "unit not found" apart from "service unavailable".
- Implemented **global error handling** (`GlobalExceptionHandler`, `GlobalFeignErrorDecoder`, a shared error DTO) in both services.
- Wrote the documentation (README) for both services.

## Tech stack

- **Java 21**, **Spring Boot 4**, **Spring Cloud** (Config Server, Gateway, OpenFeign)
- **Resilience4j** (Circuit Breaker, Retry)
- **Spring Data JPA** + **H2** in-memory database, so no external database is needed
- **MapStruct**, **Lombok**, **Maven**
- **SpringDoc OpenAPI** (Swagger UI)

## Running locally

```bash
git clone https://github.com/nikola-vasiljevic04/programiranje-sistema.git
cd programiranje-sistema
mvn clean install
```

Start the services in this order, each with `mvn spring-boot:run` from its own folder:

1. **Config Server** (`config-service`), which reads its settings from `config-repo`
2. **Auth Service**
3. Data services: **Accommodation**, **Vodici**
4. Business services: **Booking**, **Finance**, **Maintenance**, **Avanture**
5. **Gateway Service**

Each service documents its own endpoints in its folder's `README.md` and in Swagger UI (`/swagger-ui/index.html`).

## Team

- **Nikola Nedeljković**: gateway, authentication, booking & finance integration, configuration, logging
- **Nikola Vasiljević**: accommodation & maintenance services, Feign integration, error handling
- **Milan Stojičević**: adventures & guides services

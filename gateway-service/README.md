# Eco-Travel Gateway Service

This service acts as the single entry point (API Gateway) for the Eco-Travel & Adventure system, handling request routing, security filtering, and rate limiting.

## Technical Specifications
- **Framework:** Spring Cloud Gateway
- **Language:** Java 21
- **Communication:** WebClient (Asynchronous Auth Validation)
- **Port:** 8080 (Primary entry point for the entire system)

## Business Logic & Features
1. **Global Authentication Filter:** Intercepts every request to protected routes. It validates keys via the Auth Service and injects `X-Authenticated-User` and `X-User-Role` headers for downstream microservices.
2. **Advanced Rate Limiting:** Implements a tiered rate-limiting strategy. It distinguishes between **READ** (GET) and **WRITE** (POST/PUT/DELETE) operations to prevent system abuse while maintaining high availability for data browsing.
3. **Observability & Performance Tracking:** Generates unique `X-Trace-ID` tokens for distributed tracing and calculates server-side processing time returned in the `X-Processing-Time-Ms` header.

## Integrated Security Policies
The Gateway enforces a strict permission matrix. Examples include:
- **Admin Only:** Resource creation (Units, Addresses, Workers, Tasks).
- **Guest Only:** Booking lifecycle management (Create, Patch, Delete).
- **Public:** Authentication, Swagger documentation, and Actuator health checks.

## Local Setup & Testing

### Prerequisites
- **Auth Service** must be running on port `8079`.
- **Config Server** must be running on port `8888`.

### Run
```bash
mvn spring-boot:run
```

### Accessing the System
All API requests should be directed through the Gateway:
- Base URL: http://localhost:8080/

# Eco-Travel Auth Service

This microservice is responsible for identity management, user authentication, and Role-Based Access Control (RBAC). It acts as the central authority for validating security credentials across the ecosystem.

## Technical Specifications
- **Framework:** Spring Boot 4.0.3
- **Language:** Java 21
- **Database:** H2 (In-Memory for development/testing)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8079 (Standard port for authentication)

## Business Logic & Features
1. **API Key Validation:** Provides a dedicated validation endpoint for the API Gateway to verify incoming `X-API-KEY` headers and retrieve user metadata.
2. **Role-Based Access Control (RBAC):** Manages user permissions (GUEST, GUIDE, ADMIN). Role elevation is restricted to administrative users.
3. **Session Management:** Handles secure login and logout procedures, ensuring that invalidated keys are immediately rejected by the system.

## API Endpoints

### Authentication
| Method   | Endpoint             | Description                                     |
|:---------|:---------------------|:------------------------------------------------|
| `POST`   | `/api/auth/register` | Register a new user profile                     |
| `POST`   | `/api/auth/login`    | Login user and receive an API Key               |
| `GET`    | `/api/auth/validate` | Internal: Validate API Key and return User info |
| `DELETE` | `/api/auth/logout`   | Invalidate current session/API Key              |

### Management
| Method   | Endpoint             | Description                                     |
|:---------|:---------------------|:------------------------------------------------|
| `PATCH`  | `/api/auth/role`     | Update user roles (Admin only)                  |

## Local Setup & Testing

### Run
```bash
mvn spring-boot:run
```
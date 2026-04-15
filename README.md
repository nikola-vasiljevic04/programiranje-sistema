# Eco-Travel & Adventure Management System

Welcome to the central repository for the **Eco-Travel & Adventure** project. This system is a modern, distributed platform for managing eco-friendly travel, accommodation, maintenance, and adventures. It is developed using a microservices architecture as part of the **System Programming** course.

---

## System Architecture

The system is designed as a suite of autonomous microservices that communicate with each other via REST APIs. The architecture relies heavily on the Spring Cloud ecosystem to ensure scalability, fault tolerance, and resilience.

### Microservices Overview
| Microservice               | Primary Responsibility                              | Port   |
|:---------------------------|:----------------------------------------------------|:-------|
| **Config Server**          | Centralized configuration for all services          | `8888` |
| **Gateway Service**        | Single entry point, routing, and security           | `8080` |
| **Authentication Service** | Authentication                                      | `8079` |
| **Accommodation Service**  | Accommodation catalog and availability calendar     | `8081` |
| **Maintenance Service**    | Unit maintenance and work orders                    | `8082` |
| **Avanture Service**       | Adventure orchestration (Facade & Factory patterns) | `8083` |
| **Vodici Service**         | Guide profiling, certifications, and verification   | `8084` |
| **Booking Service**        | Reservation management and price calculation        | `8090` |
| **Finance Service**        | Invoicing, taxes, and transactions                  | `8091` |

---

## Synergy and Integration (Phase 3)

In the third phase of the project, the focus was on integrating the individual microservices into a single, cohesive ecosystem:

* **Centralized Configuration:** All services fetch their configuration parameters from the `Config Server`. This allows for dynamic system management and updates without the need to repackage or redeploy the code.
* **Resilience & Fault Tolerance:** **Circuit Breaker** and **Retry** mechanisms (via Resilience4j) are implemented at critical integration points (e.g., Booking -> Accommodation). This prevents cascading failures across the system if a single service becomes unavailable.
* **API Gateway & Security:** A unified entry point is established. The Gateway handles authentication and propagates security headers (`X-Authenticated-User`, `X-User-Role`) to downstream internal services, ensuring secure and stateless communication.
* **Inter-service Communication:** **OpenFeign** is used for clean, declarative REST communication between services. A custom **ErrorDecoder** is implemented for uniform error handling (e.g., smoothly mapping downstream 404 errors into proper business exceptions).

---

## Technology Stack

- **Framework:** Spring Boot 4.0.3, Spring Cloud
- **Language:** Java 21
- **Database:** H2 (In-memory) - allows for quick startup with no external dependencies
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Communication:** OpenFeign, REST
- **Utilities:** Lombok, MapStruct, Maven

---

## How to Run the System

To run the complete system locally, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git](https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git)
    cd ps-project-2025-26
    ```
2.  **Install dependencies and build:**
    ```bash
    mvn clean install
    ```
3.  **Startup Order:**
    * First, start the **Config Server** (ensure it successfully points to your `config-repo`).
    * Next, start the foundational data services (**Accommodation**, **Vodici**).
    * Then, start the business logic services (**Booking**, **Finance**, **Maintenance**, **Avanture**).
    * Finally, start the **Gateway Service**.

*Note: Each microservice can be started from its respective directory using the `mvn spring-boot:run` command.*

---

## Team (Synergy)

This project is the result of collaborative teamwork by:
- **Nikola Nedeljković**
- **Nikola Vasiljević**
- **Milan Stojičević**

# Eco-Travel Maintenance Service

This microservice handles the operational and upkeep layer of the Eco-Travel & Adventure management system, managing maintenance staff, tasks, and scheduling work orders for accommodation units.

## Technical Specifications
- **Framework:** Spring Boot 4.0.3
- **Language:** Java 21+
- **Database:** H2 (In-Memory for development/testing)
- **Mappers:** MapStruct (Entity-DTO conversion)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8082 (Standard port for this microservice)

## Business Logic & Features
1. **Work Order Management:** Creates and schedules maintenance work orders, linking specific accommodation units to workers and tasks (e.g., plumbing, cleaning).
2. **Staff Workload Tracking:** Calculates the current workload of individual workers to ensure fair distribution of tasks and prevent overbooking.
3. **Cross-Service Validation:** When scheduling a work order, the system verifies if the requested `unitId` actually exists in the Accommodation Service.
4. **Task Standardization:** Maintains a catalog of standard maintenance tasks (e.g., electrical repair, deep cleaning) that can be assigned.

## API Endpoints

### Work Orders
| Method   | Endpoint                                          | Description                                      |
|:---------|:--------------------------------------------------|:-------------------------------------------------|
| `GET`    | `/api/work-orders`                                | List all scheduled work orders                   |
| `GET`    | `/api/work-orders/{id}`                           | Find a specific work order by ID                 |
| `POST`   | `/api/work-orders`                                | Create a new work order for a unit               |
| `PUT`    | `/api/work-orders/{id}/complete`                  | Mark a work order as COMPLETED                   |
| `DELETE` | `/api/work-orders/{id}`                           | Delete a work order                              |
| `GET`    | `/api/work-orders/workers/{workerId}/workload`    | Calculate the current workload of a worker       |

### Maintenance Staff (Workers)
| Method   | Endpoint                 | Description                                            |
|:---------|:-------------------------|:-------------------------------------------------------|
| `GET`    | `/api/workers`           | Retrieve all workers (name, specialty)                 |
| `POST`   | `/api/workers`           | Add a new maintenance worker                           |

### Maintenance Tasks
| Method   | Endpoint                 | Description                                             |
|:---------|:-------------------------|:--------------------------------------------------------|
| `GET`    | `/api/tasks`             | Show all available task types                           |
| `POST`   | `/api/tasks`             | Add a new type of maintenance task                      |

## Local Setup & Testing

### Prerequisites
- JDK 21 or higher
- Maven 3.6+

### Steps to Run

#### Clone the repository:
```bash
git clone [https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git](https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git)
cd maintenance-service
```
#### Build the project:
```bash
mvn clean install
```

#### Run the application:
```bash
mvn spring-boot:run
```

#### Access Swagger Documentation:
Once the service is running, navigate to:
http://localhost:8082/swagger-ui.html
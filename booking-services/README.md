# Eco-Travel Booking Service

This microservice handles the core reservation logic and financial calculations for the Eco-Travel & Adventure management system.

## Technical Specifications
- **Framework:** Spring Boot 3.x
- **Language:** Java 17+
- **Database:** H2 (In-Memory for development/testing)
- **Mappers:** MapStruct (Entity-DTO conversion)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8090 (Standard port for this microservice)


## Business Logic & Features
1. **Status History Tracking:** Every time a booking status is updated, the system automatically generates a BookingStatusLog entry, preserving the audit trail of status changes.
2. **Dynamic Pricing (Add-ons):** Adding or removing AddOnItem (e.g. parking, breakfast) to a booking automatically recalculates the totalPrice of the reservation.
3. **Pagination:** All major listing endpoints support Spring Data Pageable for optimized performance.


## API Endpoints

### Bookings
| Method   | Endpoint                    | Description                            |
|:---------|:----------------------------|:---------------------------------------|
| `GET`    | `/api/bookings`             | Retrieve all bookings (Paginated)      |
| `GET`    | `/api/bookings/{id}`        | Get detailed booking info              |
| `POST`   | `/api/bookings`             | Create a new reservation               |
| `DELETE` | `/api/bookings/{id}`        | Remove a booking                       |
| `PATCH`  | `/api/bookings/{id}/status` | Update status (triggers history log)   |
| `GET`    | `/api/bookings/{id}/logs`   | Get status history                     |
| `GET`    | `/api/bookings/{id}/addons` | List extra services for a booking      |
| `POST`   | `/api/bookings/{id}/addons` | Add extra service (recalculates price) |

### Guests
| Method   | Endpoint           | Description                 |
|:---------|:-------------------|:----------------------------|
| `GET`    | `/api/guests`      | List all guests (Paginated) |
| `POST`   | `/api/guests`      | Register a new guest        |
| `DELETE` | `/api/guests/{id}` | Delete guest                |

### Cancellation Policies
| Method   | Endpoint             | Description                |
|:---------|:---------------------|:---------------------------|
| `GET`    | `/api/policies`      | Get all available policies |
| `POST`   | `/api/policies`      | Create a new policy        |
| `DELETE` | `/api/policies/{id}` | Remove policy              |

### Add-ons
| Method   | Endpoint           | Description                                |
|:---------|:-------------------|:-------------------------------------------|
| `GET`    | `/api/addons/{id}` | Get specific add-on details                |
| `PUT`    | `/api/addons/{id}` | Update add-on (recalculates booking total) |
| `DELETE` | `/api/addons/{id}` | Remove add-on (decreases booking total)    |

## Local Setup & Testing

### Prerequisites
- JDK 17 or higher
- Maven 3.6+

### Steps to Run

#### Clone the repository:
```bash
git clone https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git
cd booking-services
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
Open your browser and navigate to:
http://localhost:8090/swagger-ui.html
# Eco-Travel Accommodation Service

This microservice handles the lodging layer of the Eco-Travel & Adventure management system, managing accommodation units, location addresses, availability calendars, and advanced analytics.

## Technical Specifications
- **Framework:** Spring Boot 4.0.3
- **Language:** Java 21+
- **Database:** H2 (In-Memory for development/testing)
- **Mappers:** MapStruct (Entity-DTO conversion)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8081 (Standard port for this microservice)

## Business Logic & Features
1. **Dynamic Price Calculation:** Calculates real-time prices for units based on base price, active seasonal price tiers (e.g., Winter Season multipliers), and selected extra facilities (e.g., Sauna, Pool).
2. **Availability Tracking:** Manages a calendar of booked dates, allowing for strict availability checks before reservations are made.
3. **Advanced Analytics:** Provides complex query capabilities to find accommodations by specific facilities, calculate average prices by unit type, and search by city/type.
4. **Facility & Address Management:** Links units to precise geographical addresses and available facilities.

## API Endpoints

### Accommodation Units
| Method   | Endpoint                               | Description                                      |
|:---------|:---------------------------------------|:-------------------------------------------------|
| `GET`    | `/api/units`                           | Retrieve all units (supports filtering by type)  |
| `GET`    | `/api/units/{id}`                      | Get detailed unit info                           |
| `POST`   | `/api/units`                           | Create a new accommodation unit                  |
| `PUT`    | `/api/units/{id}`                      | Update an existing unit                          |
| `DELETE` | `/api/units/{id}`                      | Remove an accommodation unit                     |
| `GET`    | `/api/units/{id}/price`                | Calculate current price (with modifiers)         |
| `GET`    | `/api/units/{id}/check-availability`   | Check if the unit is available                   |

### Availability Calendar
| Method   | Endpoint                             | Description                                            |
|:---------|:-------------------------------------|:-------------------------------------------------------|
| `POST`   | `/api/availability`                  | Add a new booked period                                |
| `GET`    | `/api/availability/unit/{unitId}`    | Show all booked periods for a specific unit            |
| `DELETE` | `/api/availability/{id}`             | Remove a period from the calendar                      |

### Addresses
| Method   | Endpoint                             | Description                                            |
|:---------|:-------------------------------------|:-------------------------------------------------------|
| `GET`    | `/api/addresses`                     | Retrieve all addresses                                 |
| `POST`   | `/api/addresses`                     | Create or update an address                            |

### Analytics (Complex Queries)
| Method   | Endpoint                             | Description                                            |
|:---------|:-------------------------------------|:-------------------------------------------------------|
| `GET`    | `/api/analytics/search`              | Search by accommodation type and city                  |
| `GET`    | `/api/analytics/by-facility`         | Find all units with a specific facility                |
| `GET`    | `/api/analytics/average-price`       | Get the average price for a specific unit type         |

## Local Setup & Testing

### Prerequisites
- JDK 21 or higher
- Maven 3.6+

### Steps to Run

#### Clone the repository:
```bash
git clone [https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git](https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git)
cd accommodation-service
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
http://localhost:8081/swagger-ui.html

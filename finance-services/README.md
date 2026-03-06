# Eco-Travel Finance Service

This microservice handles the financial layer of the Eco-Travel & Adventure management system, including tax calculations, invoicing, and payment processing.

## Technical Specifications
- **Framework:** Spring Boot 3.x
- **Language:** Java 17+
- **Database:** H2 (In-Memory for development/testing)
- **Mappers:** MapStruct (Entity-DTO conversion)
- **Documentation:** SpringDoc OpenAPI (Swagger UI)
- **Port:** 8091 (Standard port for this microservice)

## Business Logic & Features
1. **Automated Tax Calculation:** When creating an `Invoice`, the system fetches the linked `TaxRate` percentage and automatically calculates the `totalAmount` based on the provided `subtotal`.
2. **Dynamic Invoicing:** Updating an invoice's subtotal via the `PATCH` endpoint triggers an immediate recalculation of the total amount.
3. **Smart Transaction Processing:** When a `Transaction` is registered for an invoice, the system checks if the amount covers the total bill. If satisfied, the invoice status is automatically updated to `PAID`.
4. **Advanced Filtering & Pagination:** The transaction filtering endpoint supports complex queries (by Invoice ID and/or Amount range) with full support for Spring Data `Pageable` (sorting and paging).

## API Endpoints

### Invoices
| Method   | Endpoint                          | Description                               |
|:---------|:----------------------------------|:------------------------------------------|
| `GET`    | `/api/invoices`                   | Retrieve all invoices                     |
| `GET`    | `/api/invoices/{id}`              | Get detailed invoice info                 |
| `POST`   | `/api/invoices`                   | Create invoice (triggers tax calculation) |
| `PATCH`  | `/api/invoices/{id}/subtotal`     | Update subtotal (triggers recalculation)  |
| `DELETE` | `/api/invoices/{id}`              | Remove an invoice                         |
| `GET`    | `/api/invoices/{id}/transactions` | List all payments for a specific invoice  |
| `POST`   | `/api/invoices/{id}/transactions` | Add a payment (can update status to PAID) |

### Transactions
| Method   | Endpoint                   | Description                                            |
|:---------|:---------------------------|:-------------------------------------------------------|
| `GET`    | `/api/transactions/filter` | **Paginated** filter by Invoice ID and/or Amount range |

### Catalog Management (Taxes & Payments)
| Method   | Endpoint                 | Description                                             |
|:---------|:-------------------------|:--------------------------------------------------------|
| `POST`   | `/api/tax-rates`         | Register a new tax rate (e.g. VAT 20%)                  |
| `POST`   | `/api/payment-methods`   | Add an accepted payment method (e.g. Credit Card, Cash) |

## Local Setup & Testing

### Prerequisites
- JDK 17 or higher
- Maven 3.6+

### Steps to Run

#### Clone the repository:
```bash
git clone [https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git](https://github.com/RAF-PS-2025-26/ps-project-2025-26-nnedeljkovic_nvasiljevic_mstojicevic.git)
cd finance-services
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
http://localhost:8091/swagger-ui.html

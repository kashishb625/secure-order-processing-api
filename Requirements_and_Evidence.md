# Requirements and Evidence

## Secure Order API

**Project:** Secure Order API  
**Developer:** Kashish Bhatnagar  
**Role:** Backend Developer Intern  
**Organization:** ArrowStack  
**Technology:** Java, Spring Boot, Spring Security, JPA/Hibernate, MySQL  
**Testing Tools:** Postman, Swagger/OpenAPI, JUnit, Mockito  
**Build Tool:** Maven  
**IDE:** Eclipse  
**Repository:** https://github.com/kashishb625/secure-order-processing-api

---

# 1. Project Overview

The Secure Order API is a secure backend REST API developed using Java and Spring Boot.

The application provides backend functionality for:

- User management
- Authentication using JWT
- Role-based authorization
- Customer management
- Product management
- Order management
- Product search and filtering
- Pagination and sorting
- Input validation
- Structured exception handling
- Password management and recovery
- Audit logging
- Transaction management
- DTO-based API responses
- Swagger/OpenAPI documentation
- Automated testing

The project was developed with a focus on security, data integrity, validation, maintainability, and reproducibility.

---

# 2. Requirements Mapping

| Requirement | Implementation | Evidence |
|---|---|---|
| REST API | Spring Boot REST controllers for Users, Customers, Products, Orders and Authentication | Postman / Swagger evidence |
| Authentication | JWT-based authentication using Spring Security | `01-login-jwt.png` |
| Protected APIs | JWT authentication required for protected endpoints | `02-protected-api.png` |
| Invalid Token Handling | Invalid/expired JWT returns HTTP 401 | `03-invalid-jwt-401.png` |
| Role-Based Authorization | USER and ADMIN roles implemented using Spring Security | `04-user-authorization-403.png` |
| User Ownership | Users can access only their own customer/order data | `05-user-ownership-403.png` |
| Input Validation | Jakarta Bean Validation with `@Valid` and validation annotations | `06-validation-400.png` |
| Structured Errors | Global exception handler with standardized `ApiResponse` | `03`, `04`, `05`, `06` evidence |
| Password Management | Secure password update using BCrypt | Application implementation |
| Password Recovery | Time-limited UUID-based password reset token | `07-password-recovery.png` |
| Audit Logging | Audit events stored in `audit_logs` table | Application implementation |
| Transaction Management | `@Transactional` used for important order operations | Application implementation |
| Product Search | Search by product name | `08-product-search.png` |
| Product Filtering | Filtering by price, stock, name and combined conditions | `08-product-search.png` |
| Pagination | Spring Data `Pageable` implementation | `09-pagination-sorting(1).png`, `09-pagination-sorting-response.png` |
| Sorting | Sorting through `Pageable` parameters | `09-pagination-sorting(1).png`, `09-pagination-sorting-response.png` |
| API Documentation | Swagger/OpenAPI with JWT Bearer authentication | `10-swagger-openapi-authorize.png`, `10-swagger-openapi-endpoint.png`, `10-swagger-openapi-response.png` |
| Automated Testing | JUnit and Mockito service-layer tests | `11-automated-tests.png` |
| Version Control | Git and GitHub used throughout development | GitHub repository |
| Documentation | README and requirements/evidence documentation | Repository documentation |

---

# 3. REST API Implementation

The application follows a REST-based architecture using Spring Boot.

### Main API Areas

- `/api/auth`
- `/api/users`
- `/api/customers`
- `/api/products`
- `/api/orders`

### Supported Operations

- Create
- Read
- Update
- Delete
- Search
- Filter
- Pagination
- Sorting
- Authentication
- Password recovery

The API was tested using Postman and Swagger/OpenAPI.

---

# 4. Authentication

## JWT Authentication

JWT-based authentication was implemented using Spring Security.

### Authentication Flow

1. User submits username and password.
2. Credentials are validated.
3. Password is checked using BCrypt.
4. A JWT token is generated after successful authentication.
5. The client sends the JWT with subsequent protected requests.
6. A custom JWT authentication filter validates the token.
7. The authenticated user and role are loaded into the Spring Security context.

### Evidence

**File:** `01-login-jwt.png`

This screenshot demonstrates successful login and JWT token generation.

---

# 5. Protected API Access

Protected API endpoints require a valid JWT token.

The JWT is sent through the HTTP Authorization header:

    Authorization: Bearer <JWT_TOKEN>

### Evidence

**File:** `02-protected-api.png`

This demonstrates successful access to a protected API using a valid JWT.

---

# 6. Invalid JWT Handling

Invalid or expired JWT tokens are rejected by the authentication filter.

The API returns a standardized HTTP `401 Unauthorized` response.

Example:

    {
      "status": 401,
      "message": "Invalid or expired token",
      "data": null
    }

### Evidence

**File:** `03-invalid-jwt-401.png`

This demonstrates that invalid or expired tokens are handled with HTTP `401 Unauthorized`.

---

# 7. Role-Based Authorization

Two primary roles are implemented:

- `USER`
- `ADMIN`

Spring Security authorities are used to enforce access control.

### USER

A normal USER can:

- Access authenticated APIs
- Access their own customer information
- Access their own orders
- Perform operations allowed for their role

A USER cannot access ADMIN-only functionality.

### ADMIN

An ADMIN can:

- Access all users
- Access all customers
- Access all orders
- Perform administrative operations

### Evidence

**File:** `04-user-authorization-403.png`

This demonstrates a USER attempting to access an ADMIN-only endpoint and receiving HTTP `403 Forbidden`.

---

# 8. User-Specific Order Authorization

Order ownership was implemented to prevent users from accessing orders belonging to another user.

The authorization flow verifies:

1. Current authenticated username
2. Order's customer
3. Customer's linked user
4. Ownership before returning the order

If the order does not belong to the authenticated USER, access is rejected.

### Standardized Response

    {
      "status": 403,
      "message": "You are not allowed to access this order",
      "data": null
    }

### Evidence

**File:** `05-user-ownership-403.png`

This demonstrates that a USER cannot access another user's order.

---

# 9. Input Validation

Input validation was implemented using Jakarta Bean Validation.

Examples include:

- `@Valid`
- `@Email`
- Required input validation
- Validation of request bodies

Invalid input is rejected before reaching the business logic.

### Evidence

**File:** `06-validation-400.png`

This demonstrates an invalid request being rejected with HTTP `400 Bad Request`.

---

# 10. Exception Handling

A centralized `GlobalExceptionHandler` was implemented using `@ControllerAdvice`.

The application handles exceptions including:

- `ResourceNotFoundException`
- `MethodArgumentNotValidException`
- `InvalidCredentialsException`
- `PasswordResetException`
- `ResponseStatusException`

The application returns standardized API responses instead of exposing inconsistent default error responses.

### Standard Error Structure

    {
      "status": 400,
      "message": "Validation error message",
      "data": null
    }

---

# 11. Standard API Response

A reusable `ApiResponse` DTO was implemented.

The standard response structure contains:

- Status
- Message
- Data

Example:

    {
      "status": 200,
      "message": "Operation successful",
      "data": {}
    }

This provides a consistent response structure across the API.

---

# 12. DTO-Based Responses

DTOs were introduced to control the information returned by API endpoints.

Examples include:

- `UserResponse`
- `CustomerResponse`
- `OrderResponse`

The `OrderResponse` provides relevant order information such as:

- Order ID
- Total amount
- Status
- Order date
- Customer name
- Customer ID
- Username

DTO-based responses help prevent unnecessary entity information from being exposed directly through API responses.

---

# 13. Password Management

User passwords are stored using BCrypt hashing rather than plain text.

Password updates are authorized according to the user's identity and role.

### Password Update Rules

- A USER can change their own password.
- An ADMIN can update user passwords according to administrative access.
- Passwords are encoded using BCrypt before being stored.

Password-change events are also recorded in the audit log.

---

# 14. Password Recovery

A password recovery mechanism was implemented using temporary UUID-based reset tokens.

### Password Recovery Flow

1. User requests password recovery.
2. A unique UUID reset token is generated.
3. The token is associated with the user.
4. The token is assigned a limited expiry period.
5. The user submits the token with a new password.
6. The password is BCrypt encoded.
7. The reset token is deleted after successful use.

### Token Validity

Reset tokens are valid for a limited period of **15 minutes**.

Expired or invalid tokens are rejected.

### Evidence

**File:** `07-password-recovery.png`

This demonstrates successful generation of a password recovery token.

---

# 15. Audit Logging

An audit logging mechanism was implemented using a dedicated `audit_logs` table.

### Audit Log Fields

- ID
- Username
- Action
- Timestamp

### Important Audited Events

#### User

- `CREATE_USER`
- `LOGIN`

#### Password

- `CHANGE_PASSWORD`
- `PASSWORD_RESET_REQUESTED`
- `PASSWORD_RESET`

#### Customer

- `CREATE_CUSTOMER`
- `UPDATE_CUSTOMER`
- `DELETE_CUSTOMER`

#### Product

- `CREATE_PRODUCT`
- `UPDATE_PRODUCT`
- `DELETE_PRODUCT`

#### Order

- `CREATE_ORDER`
- `UPDATE_ORDER`
- `DELETE_ORDER`

Audit logging provides traceability for important state-changing operations.

---

# 16. Transaction Management

Transaction management was implemented using Spring's `@Transactional`.

Transactional behavior is applied to important order operations such as:

- Order creation
- Order updates
- Order deletion

This helps maintain database consistency when multiple operations are performed as part of a business operation.

---

# 17. Product Search and Filtering

Product search and filtering functionality was implemented.

Supported filters include:

### Product Name

    GET /api/products/search?name=Laptop

### Price Range

    GET /api/products/search/price?minPrice=1000&maxPrice=50000

### Stock Range

    GET /api/products/search/stock?minStock=1&maxStock=100

### Combined Name and Price

    GET /api/products/search/filter?name=Laptop&minPrice=1000&maxPrice=50000

### Combined Name, Price and Stock

    GET /api/products/search/filter/all?name=Laptop&minPrice=1000&maxPrice=50000&minStock=1&maxStock=100

### Evidence

**File:** `08-product-search.png`

This demonstrates successful product search and retrieval of matching products.

---

# 18. Pagination and Sorting

Spring Data `Pageable` was implemented for product retrieval.

Example request:

    GET /api/products?page=0&size=2&sort=price,asc

The API returns pagination information including:

- Content
- Page number
- Page size
- Total elements
- Total pages
- Sorting information

### Evidence

The pagination test was captured using two screenshots:

- `09-pagination-sorting(1).png`
- `09-pagination-sorting-response.png`

The screenshots demonstrate the request parameters and the resulting paginated and sorted response.

---

# 19. Swagger/OpenAPI Documentation

Swagger/OpenAPI documentation was implemented using Springdoc OpenAPI.

Swagger UI is available at:

    http://localhost:8080/swagger-ui/index.html

The documentation provides:

- Available REST endpoints
- HTTP methods
- Request parameters
- Request bodies
- Response structures
- Authentication support

JWT Bearer authentication was configured in Swagger.

### Swagger Authentication Flow

1. Open Swagger UI.
2. Click `Authorize`.
3. Enter the JWT token.
4. Execute a protected endpoint.
5. Verify the API response.

### Evidence

The Swagger implementation is documented using:

- `10-swagger-openapi-authorize.png`
- `10-swagger-openapi-endpoint.png`
- `10-swagger-openapi-response.png`

These screenshots demonstrate JWT authorization, protected endpoint execution, and the resulting API response.

---

# 20. Automated Testing

Automated service-layer tests were implemented using:

- JUnit
- Mockito

The tests cover important backend functionality.

### ProductServiceTest

Tests include:

- Product creation
- Product update
- Product deletion
- Product not-found exception

### OrderServiceTest

Tests include:

- Admin order creation
- Order persistence
- Audit logging

### AuthServiceTest

Tests include:

- Successful login
- User lookup
- Password verification
- JWT generation
- Login audit logging

### Evidence

**File:** `11-automated-tests.png`

This demonstrates successful execution of the implemented automated tests.

---

# 21. Testing Evidence Summary

All major security, validation, API, documentation, and automated testing scenarios were captured as evidence.

| No. | Test Area | Evidence File(s) |
|---|---|---|
| 1 | JWT Login | `01-login-jwt.png` |
| 2 | Protected API | `02-protected-api.png` |
| 3 | Invalid JWT | `03-invalid-jwt-401.png` |
| 4 | Role Authorization | `04-user-authorization-403.png` |
| 5 | User Ownership | `05-user-ownership-403.png` |
| 6 | Input Validation | `06-validation-400.png` |
| 7 | Password Recovery | `07-password-recovery.png` |
| 8 | Product Search | `08-product-search.png` |
| 9 | Pagination & Sorting | `09-pagination-sorting(1).png` |
|  |  | `09-pagination-sorting-response.png` |
| 10 | Swagger/OpenAPI | `10-swagger-openapi-authorize.png` |
|  |  | `10-swagger-openapi-endpoint.png` |
|  |  | `10-swagger-openapi-response.png` |
| 11 | Automated Tests | `11-automated-tests.png` |

---

# 22. Technology Stack

### Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Database

- MySQL

### Security

- JWT
- BCrypt
- Role-Based Authorization
- User Ownership Authorization

### API Development and Testing

- REST APIs
- Postman
- Swagger/OpenAPI

### Testing

- JUnit
- Mockito

### Build and Development

- Maven
- Eclipse
- Git
- GitHub

---

# 23. Project Architecture

The project follows a layered backend architecture.

    Client
      |
      v
    Controller Layer
      |
      v
    Service Layer
      |
      v
    Repository Layer
      |
      v
    MySQL Database

### Main Layers

#### Controller

Handles:

- HTTP requests
- Request validation
- API responses

#### Service

Handles:

- Business logic
- Authorization checks
- Password management
- Order processing
- Audit logging

#### Repository

Handles:

- Database operations
- JPA queries
- Search and filtering

#### Entity

Represents database entities such as:

- User
- Customer
- Product
- Order
- PasswordResetToken
- AuditLog

#### DTO

Controls API request and response data.

#### Security

Handles:

- JWT authentication
- Role-based authorization
- User ownership validation

---

# 24. Entity Relationships

The main relationships include:

    User
     |
     | One-to-One
     v
    Customer
     |
     | One-to-Many
     v
    Order

Additional entities include:

    Product
    PasswordResetToken
    AuditLog

### User → Customer

A user is associated with a customer profile.

### Customer → Order

A customer can have multiple orders.

### User → PasswordResetToken

A password reset token is associated with a user and has a limited expiry period.

---

# 25. Security Configuration

The application protects API endpoints using Spring Security.

### Public Endpoints

Authentication endpoints are publicly accessible:

    /api/auth/**

Swagger documentation endpoints are also publicly accessible:

    /swagger-ui/**
    /swagger-ui.html
    /v3/api-docs/**

User registration is publicly accessible:

    POST /api/users

### Protected Endpoints

The following API areas require authentication:

    /api/users/**
    /api/products/**
    /api/customers/**
    /api/orders/**

Role-specific restrictions are applied where required.

---

# 26. Database Configuration

The application uses MySQL for persistent storage.

The database contains tables corresponding to the application's entities.

Database credentials and sensitive configuration values are not hardcoded into the source code.

Environment/configuration-based values are used for sensitive information such as:

- Database password
- JWT secret

---

# 27. Version Control

Git and GitHub were used throughout development.

The project was developed through meaningful commits corresponding to major milestones.

Examples of project milestones include:

    Implement JWT authentication
    Implement user-specific order authorization
    Implement password management and recovery
    Implement pagination sorting and product filtering
    Standardize product and order API responses
    Standardize exception error responses
    Add Swagger OpenAPI documentation with JWT security
    Add automated service tests
    Add transaction management
    Finalize README documentation

The source code is maintained in the GitHub repository:

    https://github.com/kashishb625/secure-order-processing-api

---

# 28. Reproducibility

The project can be reproduced using the following general workflow:

1. Clone the GitHub repository.
2. Configure the MySQL database.
3. Configure required environment variables.
4. Import the Maven project into Eclipse or another compatible IDE.
5. Build the project using Maven.
6. Start the Spring Boot application.
7. Use Postman or Swagger UI to interact with the REST APIs.
8. Run the automated tests using JUnit.

---

# 29. Validation and Verification Approach

The project was validated through multiple methods.

### Manual API Testing

Postman was used to verify:

- Authentication
- Authorization
- CRUD operations
- Validation
- Error handling
- Password recovery
- Product search
- Pagination
- Sorting

### API Documentation Testing

Swagger UI was used to verify:

- API documentation
- JWT authentication
- Protected endpoint execution
- API responses

### Automated Testing

JUnit and Mockito were used to verify important service-layer behavior.

### Evidence Collection

Screenshots were captured for key security, API, documentation, and testing scenarios.

---

# 30. Project Status

The Secure Order API has been:

- Implemented
- Secured using JWT
- Protected using role-based authorization
- Extended with user ownership authorization
- Validated using request validation
- Equipped with centralized exception handling
- Extended with password management and recovery
- Extended with audit logging
- Extended with transaction management
- Extended with product search and filtering
- Extended with pagination and sorting
- Documented using Swagger/OpenAPI
- Tested using JUnit and Mockito
- Documented using README and requirements/evidence documentation
- Validated through manual API testing and evidence screenshots

**Project Status: Completed**

---

# 31. Project Highlights

The major technical highlights of the project include:

- Secure REST API development using Spring Boot
- JWT-based authentication
- BCrypt password hashing
- Role-Based Access Control
- User-specific resource authorization
- Centralized exception handling
- Standardized API responses
- DTO-based response design
- Password recovery using time-limited reset tokens
- Audit logging
- Transaction management
- Product search and filtering
- Pagination and sorting
- Swagger/OpenAPI documentation
- Automated JUnit/Mockito testing
- Git/GitHub version control
- Security-sensitive configuration using environment variables

---

# 32. Submission Evidence

The following evidence has been prepared for project evaluation:

    Testing Evidence/
    │
    ├── 01-login-jwt.png
    ├── 02-protected-api.png
    ├── 03-invalid-jwt-401.png
    ├── 04-user-authorization-403.png
    ├── 05-user-ownership-403.png
    ├── 06-validation-400.png
    ├── 07-password-recovery.png
    ├── 08-product-search.png
    ├── 09-pagination-sorting(1).png
    ├── 09-pagination-sorting-response.png
    ├── 10-swagger-openapi-authorize.png
    ├── 10-swagger-openapi-endpoint.png
    ├── 10-swagger-openapi-response.png
    └── 11-automated-tests.png

These files provide visual evidence for the major implemented requirements.

---

# 33. Developer

**Kashish Bhatnagar**

Java Backend Developer | Spring Boot | REST APIs | MySQL

GitHub:

https://github.com/kashishb625

Project Repository:

https://github.com/kashishb625/secure-order-processing-api

---

# 34. Conclusion

The Secure Order API demonstrates the implementation of a secure and structured Java Spring Boot backend application.

The project covers REST API development, authentication, authorization, validation, exception handling, password management, audit logging, transaction management, product search and filtering, pagination, sorting, API documentation, and automated testing.

The implementation has been manually validated through Postman and Swagger/OpenAPI and supported by automated service-layer tests.

The accompanying testing evidence and documentation provide a reproducible overview of the implemented functionality and validation performed during development.

**Project Status: Completed and Ready for Submission**

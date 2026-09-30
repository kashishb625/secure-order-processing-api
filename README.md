# 🔐 Secure Order API

A secure backend REST API built using **Java and Spring Boot** for managing users, customers, products, and orders.

The project focuses on building a structured and secure backend application with **JWT authentication, role-based authorization, resource ownership, validation, exception handling, password recovery, audit logging, transaction management, pagination, filtering, and API documentation**.

---

## 🚀 Features

### 👤 User Management

- User registration
- Secure password storage using BCrypt
- Get all users
- Get user by ID
- Update user password
- Delete user
- User roles: `USER` and `ADMIN`
- Password is not exposed in API responses
- User-specific account access

### 🔐 Authentication & Security

- JWT-based authentication
- Login using username and password
- BCrypt password hashing
- JWT token validation
- Invalid/expired JWT handling
- Role-based authorization
- Resource ownership authorization
- Protected API endpoints using Spring Security
- `USER` and `ADMIN` role support

### 🔑 Password Management & Recovery

- Secure password update for authenticated users
- Password reset request
- Password reset using time-limited reset tokens
- Reset token expiration
- Reset token invalidation after successful password reset
- Password reset validation and error handling

### 👥 Customer Management

- Create customer
- Get all customers
- Get customer by ID
- Update customer
- Delete customer
- Input validation
- User-specific customer access
- Admin access to customer resources

### 📦 Product Management

- Create product
- Get all products
- Get product by ID
- Update product
- Delete product
- Input validation
- Pagination
- Sorting
- Product name search
- Price range filtering
- Stock-based filtering
- Combined product filtering

### 🛒 Order Management

- Create order
- Get all orders
- Get order by ID
- Update order
- Delete order
- Automatic order date/time
- Customer-order relationship
- User-specific order access
- Admin access to order resources
- Transaction management for order operations

### 📝 Audit Logging

The application records important security and data-modification events, including:

- User creation
- User login
- Password changes
- Password reset requests
- Password resets
- Customer creation/update/deletion
- Product creation/update/deletion
- Order creation/update/deletion

Audit records contain the username, action, and timestamp.

### 🛡️ API Reliability

- Jakarta Bean Validation
- Global exception handling
- Resource-not-found handling
- Invalid credentials handling
- Password reset error handling
- Unauthorized and forbidden access handling
- Structured API responses
- DTO-based responses
- JSON response for invalid or expired JWT tokens
- Transaction management using `@Transactional`

---

## 🏗️ Architecture

The application follows a layered backend architecture:

```text
Client
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL Database
```

### Main Layers

- **Controller** – Handles HTTP requests and API responses
- **Service** – Contains business logic and authorization rules
- **Repository** – Handles database operations using Spring Data JPA
- **Entity** – Represents database tables and relationships
- **DTO** – Controls the data exposed through API responses
- **Security** – Handles JWT authentication and authorization
- **Audit** – Records important application and security events
- **Exception** – Handles application-specific errors

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | REST API development |
| Spring Security | Authentication & authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| MySQL | Relational database |
| Maven | Dependency management |
| Jakarta Validation | Request validation |
| Swagger / OpenAPI | API documentation |
| JUnit | Automated testing |
| Mockito | Unit testing and mocking |
| Postman | API testing |
| Eclipse | Development environment |
| Git & GitHub | Version control |

---

## 📂 Project Structure

```text
src/main/java/com/Kashish/secure_order_api
│
├── audit
│   ├── AuditLog
│   ├── AuditLogRepository
│   └── AuditLogService
│
├── controller
│   ├── AuthController
│   ├── UserController
│   ├── CustomerController
│   ├── ProductController
│   └── OrderController
│
├── service
│   ├── AuthService
│   ├── UserService
│   ├── CustomerService
│   ├── ProductService
│   ├── OrderService
│   ├── PasswordResetService
│   └── JwtService
│
├── repository
│   ├── UserRepository
│   ├── CustomerRepository
│   ├── ProductRepository
│   ├── OrderRepository
│   └── PasswordResetTokenRepository
│
├── entity
│   ├── User
│   ├── Customer
│   ├── Product
│   ├── Order
│   └── PasswordResetToken
│
├── dto
│   ├── LoginRequest
│   ├── LoginResponse
│   ├── UserResponse
│   ├── UserUpdateRequest
│   ├── CustomerResponse
│   ├── OrderResponse
│   └── ResetPasswordRequest
│
├── security
│   ├── SecurityConfig
│   └── JwtAuthenticationfilter
│
├── exception
│   ├── ResourceNotFoundException
│   ├── InvalidCredentialsException
│   ├── PasswordResetException
│   └── GlobalExceptionHandler
│
└── config
    └── OpenAPIConfig
```

---

## 🔑 Authentication Flow

The API uses JWT-based authentication.

```text
User
 │
 │ Login
 ▼
Authentication Endpoint
 │
 │ Username + Password
 ▼
AuthService
 │
 │ Verify BCrypt Password
 ▼
JWT Token Generated
 │
 ▼
Client
 │
 │ Bearer Token
 ▼
Protected API
 │
 ▼
JWT Authentication Filter
 │
 ├── Validate Token
 ├── Extract Username
 └── Load User Role
 │
 ▼
Authorization
 │
 ├── USER
 └── ADMIN
 │
 ▼
Requested Resource
```

---

## 👮 Role-Based Authorization

The application supports two roles:

### USER

A `USER` can access resources according to ownership rules.

A user can:

- Access their own customer information
- Modify their own customer information
- Delete their own customer information
- Access their own orders
- Modify their own orders
- Delete their own orders
- Update their own password

Attempting to access another user's protected resource results in a `403 Forbidden` response.

### ADMIN

An `ADMIN` has broader access to user, customer, product, and order management according to the configured security rules.

Admins can access resources belonging to different users and perform administrative operations.

---

## 🔗 Entity Relationships

The main relationships are:

```text
User
 │
 │ One-to-One
 ▼
Customer
 │
 │ One-to-Many
 ▼
Order
```

A user can have an associated customer, while a customer can have multiple orders.

Orders are associated with customers using JPA relationships.

---

## 📡 API Endpoints

### 🔐 Authentication APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/login` | Authenticate user and generate JWT |
| `POST` | `/api/auth/forgot-password` | Generate password reset token |
| `POST` | `/api/auth/reset-password` | Reset password using reset token |

### 👤 User APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/users` | Create a user |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `PUT` | `/api/users/{id}` | Update user password |
| `DELETE` | `/api/users/{id}` | Delete user |

### 👥 Customer APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/customers` | Create customer |
| `GET` | `/api/customers` | Get customers |
| `GET` | `/api/customers/{id}` | Get customer by ID |
| `PUT` | `/api/customers/{id}` | Update customer |
| `DELETE` | `/api/customers/{id}` | Delete customer |

### 📦 Product APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/products` | Create product |
| `GET` | `/api/products` | Get products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `PUT` | `/api/products/{id}` | Update product |
| `DELETE` | `/api/products/{id}` | Delete product |

Product listing also supports pagination, sorting, searching, and filtering.

### 🛒 Order APIs

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/orders` | Create order |
| `GET` | `/api/orders` | Get orders |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `PUT` | `/api/orders/{id}` | Update order |
| `DELETE` | `/api/orders/{id}` | Delete order |

---

## 🔒 Example Authorization

Protected requests require a JWT token.

Add the following HTTP header:

```text
Authorization: Bearer <your-jwt-token>
```

Example:

```http
GET /api/orders
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## ❌ Invalid JWT Response

If an invalid or expired JWT token is supplied, the API returns:

```json
{
  "status": 401,
  "message": "Invalid or expired token"
}
```

---

## 📄 Standard API Response

The application uses a standardized response structure for supported APIs:

```json
{
  "status": 200,
  "message": "Operation successful",
  "data": {}
}
```

This provides a consistent structure for successful API responses.

---

## ✅ Validation

The API uses Jakarta Bean Validation for validating incoming request data.

Examples include:

- Required fields
- Email format validation
- Request body validation using `@Valid`
- Input validation before service-layer processing

Invalid requests are rejected instead of being directly processed by the service layer.

---

## ⚠️ Exception Handling

The project provides centralized exception handling for common API errors such as:

- Resource not found
- Unauthorized access
- Forbidden resource access
- Invalid JWT token
- Invalid credentials
- Invalid request data
- Password reset errors
- Invalid resource IDs

Errors are returned using structured JSON responses.

---

## 📦 DTO-Based Responses

The project uses DTOs to control the information returned by API endpoints.

For example, user responses do not expose the user's password.

```text
Entity
   ↓
Service
   ↓
Controller
   ↓
DTO
   ↓
API Response
```

This helps prevent sensitive entity fields from being directly exposed through the REST API.

---

## 🔎 Product Search & Filtering

Product APIs support multiple filtering options, including:

- Product name search
- Minimum price
- Maximum price
- Stock filtering
- Combined name and price filtering
- Combined name, price, and stock filtering
- Pagination
- Sorting

This allows clients to retrieve only the required product data.

---

## 📝 Audit Logging

Important security and data-modification events are recorded in the `audit_logs` table.

Examples:

```text
CREATE_USER
LOGIN
CHANGE_PASSWORD
PASSWORD_RESET_REQUESTED
PASSWORD_RESET

CREATE_CUSTOMER
UPDATE_CUSTOMER
DELETE_CUSTOMER

CREATE_PRODUCT
UPDATE_PRODUCT
DELETE_PRODUCT

CREATE_ORDER
UPDATE_ORDER
DELETE_ORDER
```

Each audit record contains:

- Username
- Action
- Timestamp

---

## 🔄 Transaction Management

Transaction management is implemented using Spring's `@Transactional` annotation for order write operations.

The following operations are transactional:

- Order creation
- Order update
- Order deletion

This helps maintain database consistency by treating related database operations as a single transaction.

---

## 📚 Swagger / OpenAPI Documentation

The project includes Swagger/OpenAPI documentation for exploring and testing the REST APIs.

After starting the application, open:

`http://localhost:8080/swagger-ui/index.html`

The Swagger interface can be used to:

- Explore available endpoints
- View request and response structures
- Execute API requests
- Authorize using a JWT bearer token

Use the **Authorize** button in Swagger to provide:

```text
Bearer <your-jwt-token>
```

---

## 🧪 Automated Testing

The project includes automated service-level tests using **JUnit and Mockito**.

### ProductServiceTest

Tests include:

- Product creation
- Product update
- Product deletion
- Product-not-found exception

### OrderServiceTest

Tests include:

- Admin order creation

### AuthServiceTest

Tests include:

- Successful login with valid credentials
- JWT generation
- Login audit event

The project also includes manual API validation using Postman.

---

## 🗄️ Database

The project uses **MySQL** as the relational database and **Hibernate/JPA** for ORM.

Main entities include:

```text
User
Customer
Product
Order
PasswordResetToken
AuditLog
```

JPA relationships are used to connect related entities.

---

## ⚙️ Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/kashishb625/secure-order-processing-api.git
cd secure-order-processing-api
```

### 2. Open the project

Open the project in Eclipse or another Java IDE.

### 3. Create the MySQL database

Create a MySQL database:

```sql
CREATE DATABASE secure_order_api;
```

### 4. Configure database credentials

Configure the database connection using external configuration or environment variables.

> **Do not commit real database passwords or JWT secrets to GitHub.**

Example database URL:

```text
jdbc:mysql://localhost:3306/secure_order_api
```

Required configuration should include:

```text
Database URL
Database username
Database password
JWT secret
```

Keep sensitive values outside the source-controlled repository.

### 5. Install dependencies

Maven will download the required dependencies automatically.

```bash
mvn clean install
```

### 6. Run the application

From Eclipse, run the Spring Boot application.

Or use:

```bash
mvn spring-boot:run
```

The API will be available at:

`http://localhost:8080`

Swagger documentation:

`http://localhost:8080/swagger-ui/index.html`

---

## 🔐 Security Configuration

The application uses:

- Spring Security
- JWT authentication
- BCrypt password hashing
- Role-based authorization
- Resource ownership checks
- Secure password management
- Password reset tokens
- External configuration for sensitive credentials
- Invalid/expired JWT handling

Sensitive database credentials and JWT secrets should not be committed to source control.

---

## 🧰 Tools Used

- Eclipse IDE
- Postman
- MySQL
- Git
- GitHub
- Maven

---

## 📈 Project Status

### ✅ Completed

- Spring Boot project setup
- MySQL database integration
- User management
- Customer CRUD APIs
- Product CRUD APIs
- Order CRUD APIs
- JPA/Hibernate relationships
- Request validation
- Global exception handling
- Resource-not-found handling
- BCrypt password hashing
- JWT authentication
- Invalid/expired JWT handling
- Role-based authorization
- User-specific customer authorization
- User-specific order authorization
- Password update
- Password recovery/reset
- DTO-based API responses
- Standardized API responses
- Pagination
- Sorting
- Product search and filtering
- Audit logging
- Swagger/OpenAPI documentation
- Automated service tests using JUnit and Mockito
- Transaction management
- Postman API testing
- Git/GitHub version control

---

## 📌 Project Highlights

This project demonstrates practical backend development concepts including:

- RESTful API development
- Object-Oriented Programming
- Layered architecture
- Database design
- JPA/Hibernate
- MySQL
- Spring Security
- JWT authentication
- Role-Based Access Control
- Resource ownership
- BCrypt password hashing
- Password recovery
- Input validation
- Global exception handling
- DTO design
- Pagination
- Sorting
- Search and filtering
- Audit logging
- Transaction management
- Swagger/OpenAPI
- Automated unit testing
- Mockito
- Postman API testing
- Git/GitHub workflow

---

## 👨‍💻 Developer

**Kashish Bhatnagar**

Java Backend Developer | Spring Boot | REST APIs | MySQL

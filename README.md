# 🔐 Secure Order API

A secure backend REST API built using **Java and Spring Boot** for managing users, customers, products, and orders.

The project focuses on building a structured backend application with **JWT authentication, role-based authorization, user-specific resource access, validation, exception handling, JPA/Hibernate, and MySQL**.

---

## 🚀 Features

### 👤 User Management

- User registration
- Secure password storage using **BCrypt**
- Get all users
- Get user by ID
- Delete user
- User roles: `USER` and `ADMIN`
- Password is not exposed in API responses

### 🔐 Authentication & Security

- JWT-based authentication
- Login using username and password
- Secure password hashing with BCrypt
- JWT token validation
- Invalid/expired JWT handling
- Role-based authorization
- Protected API endpoints using Spring Security

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

### 🛡️ API Security & Reliability

- Role-based access control
- Resource ownership authorization
- Bean Validation
- Custom resource-not-found handling
- Unauthorized access handling
- Structured DTO-based API responses
- JSON response for invalid or expired JWT tokens

---

## 🏗️ Architecture

The application follows a layered backend architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Main Layers

- **Controller** – Handles HTTP requests and responses
- **Service** – Contains business logic
- **Repository** – Handles database operations using Spring Data JPA
- **Entity** – Represents database tables
- **DTO** – Controls the data exposed through API responses
- **Security** – Handles JWT authentication and authorization

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| Java | Backend programming |
| Spring Boot | REST API development |
| Spring Security | Authentication & authorization |
| JWT | Token-based authentication |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| MySQL | Relational database |
| Maven | Dependency management |
| Jakarta Validation | Request validation |
| Postman | API testing |
| Eclipse | Development environment |
| Git & GitHub | Version control |

---

## 📂 Project Structure

```text
src/main/java/com/Kashish/secure_order_api
│
├── controller
│   ├── UserController
│   ├── CustomerController
│   ├── ProductController
│   └── OrderController
│
├── service
│   ├── UserService
│   ├── CustomerService
│   ├── ProductService
│   ├── OrderService
│   └── JwtService
│
├── repository
│   ├── UserRepository
│   ├── CustomerRepository
│   ├── ProductRepository
│   └── OrderRepository
│
├── entity
│   ├── User
│   ├── Customer
│   ├── Product
│   └── Order
│
├── dto
│   ├── UserResponse
│   ├── CustomerResponse
│   └── OrderResponse
│
├── security
│   ├── SecurityConfig
│   └── JwtAuthenticationfilter
│
└── exception
    └── ResourceNotFoundException
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
Spring Security
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

For example:

- Access their own customer information
- Modify their own customer information
- Delete their own customer information
- Access their own orders
- Modify their own orders
- Delete their own orders

Attempting to access another user's protected resource results in a `403 Forbidden` response.

### ADMIN

An `ADMIN` has broader access to customer and order resources and can manage resources belonging to different users.

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

### 👤 User APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users` | Create a user |
| `GET` | `/api/users` | Get all users |
| `GET` | `/api/users/{id}` | Get user by ID |
| `DELETE` | `/api/users/{id}` | Delete user |

---

### 👥 Customer APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/customers` | Create customer |
| `GET` | `/api/customers` | Get customers |
| `GET` | `/api/customers/{id}` | Get customer by ID |
| `PUT` | `/api/customers/{id}` | Update customer |
| `DELETE` | `/api/customers/{id}` | Delete customer |

---

### 📦 Product APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/products` | Create product |
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `PUT` | `/api/products/{id}` | Update product |
| `DELETE` | `/api/products/{id}` | Delete product |

---

### 🛒 Order APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
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

```text
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

## ✅ Validation

The API uses Jakarta Bean Validation for validating incoming request data.

Examples include:

- Required fields
- Email format validation
- Request body validation using `@Valid`

Invalid requests are rejected instead of being directly processed by the service layer.

---

## ⚠️ Exception Handling

The project handles common API errors such as:

- Resource not found
- Unauthorized resource access
- Invalid JWT token
- Invalid request data
- Invalid resource IDs

Example:

```text
Customer with ID 100 not found
```

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

## 🗄️ Database

The project uses **MySQL** as the relational database and **Hibernate/JPA** for ORM.

Main entities include:

```text
User
Customer
Product
Order
```

The application uses JPA relationships to connect related entities.

---

## 🧪 API Testing

The APIs are tested using **Postman**.

Testing includes:

- User registration
- Authentication
- JWT-protected requests
- Customer CRUD operations
- Product CRUD operations
- Order CRUD operations
- Validation testing
- Invalid resource testing
- Unauthorized access testing
- Role-based authorization testing
- Invalid JWT testing

---

## ⚙️ Setup & Installation

### 1. Clone the repository

```bash
git clone <your-github-repository-url>
```

### 2. Open the project

Open the project in **Eclipse** or another Java IDE.

### 3. Configure MySQL

Create a MySQL database:

```sql
CREATE DATABASE secure_order_api;
```

Configure your database connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/secure_order_api
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Important:** Do not commit real database passwords or JWT secrets to GitHub.

### 4. Install dependencies

Maven will download the required dependencies automatically.

```bash
mvn clean install
```

### 5. Run the application

Run the Spring Boot application from Eclipse or using:

```bash
mvn spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

---

## 🔐 Security Configuration

The application uses:

- Spring Security
- JWT authentication
- BCrypt password hashing
- Role-based authorization
- Resource ownership checks

JWT secrets and database credentials should be stored securely using environment variables or other external configuration rather than committing sensitive values to source control.

---

## 🧰 Tools Used

- **Eclipse IDE**
- **Postman**
- **MySQL**
- **Git**
- **GitHub**
- **Maven**

---

## 📈 Current Project Progress

### Completed

- [x] Spring Boot project setup
- [x] MySQL database integration
- [x] User management
- [x] Customer CRUD APIs
- [x] Product CRUD APIs
- [x] Order CRUD APIs
- [x] JPA/Hibernate relationships
- [x] Request validation
- [x] Resource-not-found handling
- [x] BCrypt password hashing
- [x] JWT authentication
- [x] Role-based authorization
- [x] User-specific customer authorization
- [x] User-specific order authorization
- [x] DTO-based API responses
- [x] Invalid/expired JWT JSON response
- [x] Postman API testing
- [x] GitHub version control

### Planned Enhancements

- [ ] Swagger / OpenAPI API documentation
- [ ] Centralized global exception handling
- [ ] Additional authorization improvements
- [ ] Automated unit and integration tests
- [ ] Improved API documentation
- [ ] Production-oriented configuration

---

## 📌 Project Highlights

This project demonstrates practical backend development concepts including:

- RESTful API development
- Object-Oriented Programming
- Layered architecture
- Database design
- JPA/Hibernate
- Authentication
- Authorization
- JWT security
- Role-based access control
- Resource ownership
- Password encryption
- Validation
- Exception handling
- DTO design
- API testing
- Git/GitHub workflow

---

## 👨‍💻 Developer

**Kashish Bhatnagar**

B.Tech Computer Science & Technology  
Future Institute of Engineering and Technology, Bareilly

**Focus:** Java Backend Development

---

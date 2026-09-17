# 🔐 Secure Order Processing API

A backend REST API built using Java and Spring Boot for managing Products, Customers, and Orders.

This project is being developed as part of my Backend Development Internship at ArrowStack.

## 🛠️ Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Postman
- Eclipse IDE
- Git & GitHub

## ✨ Features

### Product API

- Create Product
- Get All Products
- Get Product by ID
- Update Product
- Delete Product

### Customer API

- Create Customer
- Get All Customers
- Get Customer by ID
- Update Customer
- Delete Customer

### Order API

- Create Order
- Get All Orders
- Get Order by ID
- Update Order
- Delete Order
- Customer-Order Relationship
- Automatic Order Date

## 🔗 API Endpoints

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/products | Create a product |
| GET | /api/products | Get all products |
| GET | /api/products/{id} | Get product by ID |
| PUT | /api/products/{id} | Update a product |
| DELETE | /api/products/{id} | Delete a product |

### Customers

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/customers | Create a customer |
| GET | /api/customers | Get all customers |
| GET | /api/customers/{id} | Get customer by ID |
| PUT | /api/customers/{id} | Update a customer |
| DELETE | /api/customers/{id} | Delete a customer |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/orders | Create an order |
| GET | /api/orders | Get all orders |
| GET | /api/orders/{id} | Get order by ID |
| PUT | /api/orders/{id} | Update an order |
| DELETE | /api/orders/{id} | Delete an order |

## 🗄️ Database

MySQL is used for storing application data.

Database name: secure_order_api

Main entities:

- Product
- Customer
- Order

The Order entity has a Many-to-One relationship with the Customer entity using JPA/Hibernate.

## 🏗️ Project Architecture

The application follows a layered backend architecture:

Controller → Service → Repository → Database

### Layers

**Controller**
- Handles HTTP requests
- Provides REST API endpoints

**Service**
- Contains application logic
- Processes requests

**Repository**
- Uses Spring Data JPA
- Handles database operations

**Entity**
- Represents database tables
- Defines relationships between entities

## 📂 Project Structure

- src/main/java
- controller
- entity
- repository
- service
- src/main/resources
- application.properties
- src/test
- pom.xml
- .gitignore
- README.md

## 🧪 Testing

The APIs are tested using Postman.

Current testing covers:

- Product CRUD operations
- Customer CRUD operations
- Order CRUD operations
- Customer-Order relationship
- Successful API requests and responses

## 🔐 Security & Validation

Security and validation features are planned as part of the further development of this project.

Planned features include:

- Input Validation
- Exception Handling
- Authentication & Authorization
- JWT Security
- Role-Based Access Control
- Logging & Audit
- Automated Testing

## 🚀 Future Enhancements

- Input validation
- Global exception handling
- JWT authentication
- Role-based authorization
- Logging and audit functionality
- Transaction management
- JUnit and Mockito testing
- API documentation
- Additional edge-case handling

## ▶️ How to Run

### Prerequisites

- Java
- Maven
- MySQL
- Eclipse or another Java IDE
- Postman

### Database Setup

Create a MySQL database named:

secure_order_api

Configure the database credentials in:

src/main/resources/application.properties

The database password is supplied using the DB_PASSWORD environment variable.

### Start the Application

Run the Spring Boot application.

The API will be available at:

http://localhost:8080

### Test the APIs

Use Postman to send requests to the available endpoints.

## 📈 Project Status

🚧 Currently in Development

### Completed

- [x] Spring Boot project setup
- [x] MySQL database integration
- [x] Product CRUD API
- [x] Customer CRUD API
- [x] Order CRUD API
- [x] Customer-Order JPA relationship
- [x] Automatic order date handling
- [x] Postman API testing
- [x] Git & GitHub repository setup

### Planned

- [ ] Input Validation
- [ ] Exception Handling
- [ ] Authentication & Authorization
- [ ] JWT Security
- [ ] Logging & Audit
- [ ] Automated Testing
- [ ] API Documentation
- [ ] Transaction Management

## 👨‍💻 Developer

**Kashish Bhatnagar**

B.Tech — Computer Science & Technology

**Focus:** Java Backend Development

---

⭐ This project is being continuously developed as part of my Backend Development Internship at ArrowStack.

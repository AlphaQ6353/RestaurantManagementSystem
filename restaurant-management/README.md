# Restaurant Management System

A **Spring Boot + PostgreSQL** application for digitizing restaurant operations.  
It covers **user authentication, table booking, order processing, kitchen workflow, and billing** with **JWT-based security**.

---

## Features
- **Authentication & Security**
  - JWT-based login & signup (`AuthController`)
  - Role-based access (`ADMIN`, `MANAGER`, `WAITER`, `CUSTOMER`)
- **User Management**
  - CRUD APIs for users
- **Table Booking**
  - Book, update, cancel tables
- **Order Management**
  - Place & track orders with statuses: `PLACED → IN_KITCHEN → READY → SERVED`
- **Billing & Reports**
  - Bill generation per order
  - Daily sales report
- **Unit Tests**
  - JUnit + Mockito for services & controllers

---

## Tech Stack
- **Backend:** Spring Boot (Java 17+)
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Auth:** JWT
- **Build Tool:** Maven
- **Testing:** JUnit, Mockito

---

## Setup & Installation

### Clone Repository
```bash
git clone https://github.com/your-username/restaurant-management.git
cd restaurant-management
```

### Configure Database
Edit **`src/main/resources/application.properties`**:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/restaurant_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_jwt_secret
jwt.expiration=86400000
```

### Build & Run
```bash
mvn clean install
mvn spring-boot:run
```
App runs at: **`http://localhost:8080`**

### Run Tests
```bash
mvn test
```

---

## API Documentation

### Authentication

#### Register User
**POST** `/auth/register`  
Request:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePass123",
  "role": "CUSTOMER"
}
```
Response:
```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CUSTOMER"
}
```

#### Login
**POST** `/auth/login`  
Request (`LoginRequest`):
```json
{
  "email": "john@example.com",
  "password": "securePass123"
}
```
Response (`JwtResponse`):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR...",
  "type": "Bearer",
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "CUSTOMER"
}
```

---

### User Management

#### Get All Users
**GET** `/users`  
Response:
```json
[
  {
    "id": 1,
    "name": "Admin User",
    "email": "admin@restaurant.com",
    "role": "ADMIN"
  }
]
```

#### Update User
**PUT** `/users/{id}`  
Request:
```json
{
  "name": "Updated Name",
  "email": "updated@example.com",
  "role": "MANAGER"
}
```
Response: `200 OK`

---

### Table Booking

#### Book Table
**POST** `/tables/book`  
Request:
```json
{
  "customerId": 2,
  "tableNumber": 5,
  "bookingTime": "2025-08-16T19:00:00"
}
```
Response:
```json
{
  "id": 10,
  "customerId": 2,
  "tableNumber": 5,
  "bookingTime": "2025-08-16T19:00:00",
  "status": "BOOKED"
}
```

#### Get All Tables
**GET** `/tables`  
Response:
```json
[
  {
    "id": 10,
    "customerId": 2,
    "tableNumber": 5,
    "status": "BOOKED"
  }
]
```

---

### Orders

#### Place Order
**POST** `/orders`  
Request:
```json
{
  "customerId": 2,
  "tableId": 10,
  "items": ["Pizza", "Pasta", "Coke"]
}
```
Response:
```json
{
  "id": 101,
  "customerId": 2,
  "tableId": 10,
  "items": ["Pizza", "Pasta", "Coke"],
  "status": "PLACED"
}
```

#### Update Order Status
**PUT** `/orders/{id}/status`  
Request:
```json
{
  "status": "IN_KITCHEN"
}
```
Response:
```json
{
  "id": 101,
  "status": "IN_KITCHEN"
}
```

---

### Billing & Reports

#### Generate Bill
**GET** `/bills/{orderId}`  
Response:
```json
{
  "orderId": 101,
  "items": ["Pizza", "Pasta", "Coke"],
  "totalAmount": 550,
  "status": "PAID"
}
```

#### Daily Sales Report
**GET** `/reports/daily`  
Response:
```json
{
  "date": "2025-08-16",
  "totalOrders": 25,
  "totalRevenue": 12500
}
```

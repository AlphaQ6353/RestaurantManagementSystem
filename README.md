# Restaurant Management System (Java + PostgreSQL)

A console-based Restaurant Management System built using Java and PostgreSQL. This project helps digitize the restaurant's workflow, including table bookings, order management, kitchen updates, billing, and admin functionalities.

---

## Project Structure

```
com.restaurant/
├── config/          # Database connection setup
├── model/           # Data models (Table, Order, Bill, etc.)
├── dao/             # DAO interfaces and implementations
├── service/         # Business logic layer
├── controller/      # Controllers (optional for further separation)
Main.java            # Entry point of the application
```

---

## Features

- Book tables online  
- Take and place orders  
- Real-time kitchen order notification  
- Generate bills and record payments  
- Admin operations like sales reports and menu management (extendable)  

---

## Prerequisites

- Java 8 or higher  
- PostgreSQL 12 or higher  
- IDE like IntelliJ or Eclipse (optional but recommended)  

---

## Setup Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-username/restaurant-management-system.git
   cd restaurant-management-system
   ```

2. **Configure PostgreSQL**

   - Create a database named `restaurant_db`.
   - Update your credentials in `com.restaurant.config.DBConnection`.

3. **Run the SQL Scripts**

   ```sql
   CREATE TABLE tables (
       table_id INT PRIMARY KEY,
       capacity INT NOT NULL,
       is_booked BOOLEAN NOT NULL
   );

   CREATE TABLE orders (
       order_id SERIAL PRIMARY KEY,
       table_id INT NOT NULL,
       status VARCHAR(50) DEFAULT 'Pending'
   );

   CREATE TABLE order_items (
       order_item_id SERIAL PRIMARY KEY,
       order_id INT,
       item_id INT,
       quantity INT
   );

   CREATE TABLE bills (
       bill_id SERIAL PRIMARY KEY,
       order_id INT NOT NULL,
       total_amount NUMERIC(10, 2) NOT NULL,
       payment_status VARCHAR(20) DEFAULT 'Unpaid',
       paid_at TIMESTAMP
   );
   ```

4. **Insert Sample Data**

   ```sql
   INSERT INTO tables (table_id, capacity, is_booked) VALUES
   (1, 4, false),
   (2, 2, false),
   (3, 6, true),
   (4, 4, false),
   (5, 2, false);
   ```

5. **Compile and Run**

   Compile and run the `Main.java` file from your IDE or terminal.

---

## How to Use

- On running, the system will prompt a menu to:
  - View and book tables
  - Place orders
  - Mark order as prepared
  - Generate and pay bills
  - Exit the system

---

## Technologies Used

- Java (Core + JDBC)  
- PostgreSQL  
- DAO and Service design pattern  

---

## Notes for Evaluator

- Ensure PostgreSQL is running and accessible.  
- Credentials can be configured inside `DBConnection.java`.  
- All functionality runs through the console-based main loop.  

---

## License

This project is for academic/demo purposes and is open for extension or adaptation.
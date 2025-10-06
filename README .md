#  HotelSystem API

HotelSystem is a RESTful API built with **Java** and **Spring Boot** that allows you to manage hotel room reservations. It handles room inventory, user registrations, bookings  and payment tracking.

## Tech Stack

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Spring Security (all the endpoints are public by now)
- MySQL
- Maven

##  Features

-  User management (basic setup)
-  Full CRUD for hotel rooms
-  Booking system
-  Payment creation with status (`PENDING`, `COMFIRMED`, `CANCELLED`)
-  Tested using Postman

##  Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/RainerRodriguez/HotelSystem.git
cd HotelSystem
```

### 2. Set Up the MySQL Database

```sql
CREATE DATABASE hotelsystem_db;
```

### 3. Configure the Database Connection

Edit your `src/main/resources/application.properties` file if necessary:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hotelsystem_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 4. Run the Application

You can start the app using your IDE or:

```bash
./mvnw spring-boot:run
```

##  API Endpoints

Base URL: `http://localhost:8080`

###  User Endpoints

| Method | Endpoint           | Description                   |
|--------|--------------------|-------------------------------|
| GET    | /api               | List all users                |
| GET    | /api/{id}          | Get User by id                |
| POST   | /api/auth/register | Create a new user             |
| POST   | /api/auth/login    | login with email and password |
| PUT    | /api/{id}          | Update a room by ID           |
| DELETE | /api/{id}          | Delete a room by ID           |


###  Room Endpoints

| Method | Endpoint       | Description            |
|--------|----------------|------------------------|
| GET    | api/rooms      | List all rooms         |
| POST   | api/rooms      | Create a new room      |
| PUT    | api/rooms/{id} | Update a room by ID    |
| DELETE | api/rooms/{id} | Delete a room by ID    |

###  Reservation Endpoints

| Method | Endpoint              | Description                        |
|--------|-----------------------|------------------------------------|
| GET    | api/reservations      | List all reservations              |
| GET    | api/reservations/{id} | Get a specific reservation         |
| POST   | api/reservations      | Create a new reservation           |
| PUT    | api/reservations/{id} | Update a reservation               |
| DELETE | api/reservations/{id} | Cancel/delete a reservation        |

###  Payment Endpoints

| Method | Endpoint          | Description                    |
|--------|-------------------|--------------------------------|
| GET    | api/payments      | List all payments              |
| GET    | api/payments/{id} | Get a specific payment         |
| POST   | api/payments      | Create a payment for a booking |
| DELETE | api/payments/{id} | Delete a payment               |


##  Postman Collection

Test all endpoints using Postman.

(HotelSystem postman_collection.json) this file is in the root directory.

Just import it into Postman and start testing!

Or you can run the app and test the endpoints using Swagger UI using the link bellow
http://localhost:8080/swagger-ui/index.html

##  Author

**Rainer Rodríguez**  
[GitHub Profile](https://github.com/RainerRodriguez)
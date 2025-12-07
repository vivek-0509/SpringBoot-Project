# 🚀 RideShare Backend

A mini Ride Sharing backend built using **Spring Boot**, **MongoDB**, **JWT Authentication**, **Input Validation**, and **Global Exception Handling**.

## 📌 Tech Stack

- Spring Boot 3.2.0
- MongoDB (Spring Data MongoDB)
- Spring Security + JWT (JJWT library)
- Jakarta Validation
- BCrypt Password Encoding

## 📁 Folder Structure

```
src/main/java/org/example/rideshare/
├── model/           # Entity classes (User, Ride, RideStatus)
├── repository/      # MongoDB repositories
├── service/         # Business logic
├── controller/      # REST endpoints
├── config/          # Security configuration
├── dto/             # Request/Response DTOs
├── exception/       # Custom exceptions & global handler
├── security/        # JWT service & filter
└── util/            # Utility classes
```

## 📊 Entities

### User
| Field    | Type   | Description                      |
|----------|--------|----------------------------------|
| id       | String | MongoDB ObjectId                 |
| username | String | Unique username (3-50 chars)     |
| password | String | BCrypt encoded password          |
| role     | String | `ROLE_USER` or `ROLE_DRIVER`     |

### Ride
| Field          | Type       | Description                      |
|----------------|------------|----------------------------------|
| id             | String     | MongoDB ObjectId                 |
| userId         | String     | Passenger ID (FK → User)         |
| driverId       | String     | Driver ID (nullable until accepted) |
| pickupLocation | String     | Pickup location                  |
| dropLocation   | String     | Drop location                    |
| status         | RideStatus | `REQUESTED` / `ACCEPTED` / `COMPLETED` |
| createdAt      | Date       | Ride creation timestamp          |

## 🔌 API Endpoints

| Role         | Method | Endpoint                            | Description              |
|--------------|--------|-------------------------------------|--------------------------|
| PUBLIC       | POST   | `/api/auth/register`                | Register new user        |
| PUBLIC       | POST   | `/api/auth/login`                   | Login & get JWT token    |
| USER         | POST   | `/api/v1/rides`                     | Request a ride           |
| USER         | GET    | `/api/v1/user/rides`                | View my rides            |
| DRIVER       | GET    | `/api/v1/driver/rides/requests`     | View pending ride requests |
| DRIVER       | POST   | `/api/v1/driver/rides/{id}/accept`  | Accept a ride            |
| USER/DRIVER  | POST   | `/api/v1/rides/{id}/complete`       | Complete a ride          |

## 🔐 JWT Authentication

All protected endpoints require the JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

The token contains: `username`, `role`, `issuedAt`, `expiry`

## 🧪 CURL Examples

### Register USER
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"1234","role":"ROLE_USER"}'
```

### Register DRIVER
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"driver1","password":"abcd","role":"ROLE_DRIVER"}'
```

### Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"1234"}'
```

### Create Ride (USER)
```bash
curl -X POST http://localhost:8081/api/v1/rides \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'
```

### View Pending Rides (DRIVER)
```bash
curl -X GET http://localhost:8081/api/v1/driver/rides/requests \
-H "Authorization: Bearer <driver_token>"
```

### Accept Ride (DRIVER)
```bash
curl -X POST http://localhost:8081/api/v1/driver/rides/{rideId}/accept \
-H "Authorization: Bearer <driver_token>"
```

### Complete Ride (USER or DRIVER)
```bash
curl -X POST http://localhost:8081/api/v1/rides/{rideId}/complete \
-H "Authorization: Bearer <token>"
```

## ⚠️ Error Response Format

```json
{
  "error": "VALIDATION_ERROR",
  "message": "Pickup location is required",
  "timestamp": "2025-12-07T17:00:00Z"
}
```

Error types: `VALIDATION_ERROR`, `NOT_FOUND`, `BAD_REQUEST`, `ACCESS_DENIED`

## 🚀 Running the Application

```bash
./mvnw spring-boot:run
```

The server runs on **http://localhost:8081**

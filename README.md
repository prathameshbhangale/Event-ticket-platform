# 🎟️ Event Ticket Platform

A Spring Boot based application for **event creation, publishing, ticket management, and purchasing** with **Keycloak authentication**.

---

## 🚀 Features

- **Authentication & Authorization** with Keycloak (OAuth2 / JWT).
- **Event Management**:
  - Create, update, delete events
  - Add multiple ticket types ( integrate QR codes )
  - Draft vs Published status
- **Ticketing System**:
  - Purchase tickets
  - Track availability
- **Public API** for browsing published events
- **Secured Endpoints** requiring bearer tokens
- API documentation via **Postman Collection** & **Swagger ui**

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3+, Spring Security, Spring Data JPA
- **Database**: PostgreSQL
- **Authentication**: Keycloak
- **Build Tool**: Maven
- **Containerization**: Docker
- **API Testing**: Postman

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/prathameshbhangale/Event-ticket-platform.git
cd Event-ticket-platform
```

##📖 API Documentation

###🔹 Swagger UI
```bash
http://localhost:8082/swagger-ui/index.html
http://localhost:8082/v3/api-docs
```

### Postman Collection
```bash
event-ticket-builder.postman_collection.json
```
( check in root directory )

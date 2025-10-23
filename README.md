# Pettest REST API

This is a Spring Boot application that provides a REST API to manage pets with CRUD (Create, Read, Update, Delete) operations. It supports two profiles to run either locally with an in-memory database or with a real SQL database (MySQL/PostgreSQL) in Docker.

---

## Features

- Basic CRUD operations on Pets.
- Uses Spring Data JPA for persistence.
- Supports flexible profiles for local development and production.
- Exception handling with HTTP status codes.
- Docker Compose setup with SQL database.

---

## Design Decisions and Project Structure

This project is structured to use Spring profiles extensively as a forward-looking design choice. The current implementation supports:

- A `local` profile using an in-memory H2 database for easy local development and testing.
- An `sql` profile connecting to a full SQL database such as MySQL or PostgreSQL in Docker.

The rationale for using profiles is to cleanly separate environment-specific configurations and implementations without code duplication. This modular approach also future-proofs the project for a planned migration to NoSQL:

- The `PetRepository` interface abstracts data access.
- Different implementations (SQL-based now, NoSQL planned for the future) can be switched easily by activating the corresponding Spring profile.
- This avoids mixing database logic and keeps persistence concerns isolated.


## Profiles and Database Options

The application supports two main Spring profiles:

### 1. `local`

- Uses an **in-memory H2 database**.
- Ideal for local development and testing without external dependencies.
- Data is ephemeral and lost between restarts.
- Active by default if no other profile is set.


### 2. `sql`

- Uses a **real SQL database** configured via Docker (MySQL or PostgreSQL).
- Suitable for production or integration environments.
- Data persists in Docker volumes.
- Requires database service running via Docker Compose.

Profiles are activated by Spring Boot via the `spring.profiles.active` environment variable or JVM argument.

---

## How to Run the Application

### Run Locally (In-memory DB)

This option runs the app with the `local` profile using the embedded H2 database:

`./gradlew bootRun --args='--spring.profiles.active=local'
`
### Run with Docker Compose (SQL DB)

1. Make sure Docker is installed and running.
2. Run:

`docker compose up
`

This will:

- Build the Spring Boot Docker image.
- Launch the SQL database (MySQL).
- Start the app container with the `sql` profile active.

The app will be accessible at: `http://localhost:8080`

---

## Running Tests

Run all unit and integration tests with:

`./gradlew test
`

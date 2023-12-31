# Contify Microservices

Welcome to Contify Microservices, a modular project with flexibility and maintainability qualities.

## Table of Contents

- [Services](#services)
  - [Api Gateway Service](#api-gateway-service)
  - [Discovery Server](#discovery-server)
  - [Contact Service](#contact-service)
  - [Tag Service](#tag-service)
  - [Auth Service](#auth-service)
- [Project Structure](#project-structure)

## Services

### Api Gateway Service

The API Gateway Service serves as the entry point for external clients. It handles routing, authentication, and other cross-cutting concerns, ensuring a seamless interaction with the microservices.

### Discovery Server

The Discovery Server plays a vital role in service discovery and registration. It allows services to locate and communicate with each other dynamically, promoting a highly scalable and fault-tolerant system.

### Contact Service

The Contact Service is responsible for managing contact-related operations. It includes components such as controllers, entities, DTOs, exceptions, external integrations, mappers, services, and repositories.

### Tag Service

The Tag Service focuses on operations related to tags. It includes controllers, entities, DTOs, exceptions, mappers, services, and repositories, providing a modular and dedicated solution for tag management.

### Auth Service

The Auth Service manages authentication and authorization aspects of the system. It includes configuration settings, controllers, entities, DTOs, exceptions, mappers, services, and repositories to handle user authentication securely.

## Project Structure

The project follows a consistent structure for each service:

- **controller**: Handles incoming HTTP requests and orchestrates the execution of service methods.
- **entity**: Represents the data model or database schema for the service.
- **dto**: Contains Data Transfer Objects for communication between different layers (Request / Response).
- **exceptions**: Defines custom exceptions for handling specific error scenarios.
- **external**: Manages external integrations or dependencies.
- **mapper**: Converts entities to DTOs and vice versa.
- **service**: Implements the business logic and interacts with the repository.
- **repository**: Manages database operations and data access.

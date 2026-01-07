# Electric Vehicle Dealer Management System

A comprehensive Spring Boot application designed to manage electric vehicle (EV) dealerships. This system handles everything from car inventory and hierarchical dealer structures to test drive bookings, price programs, and sales orders.

## Overview

The Electric Vehicle Dealer Management System (EVDMS) provides a robust backend to streamline dealer operations. It supports a multi-level dealer hierarchy with automated commission calculations, detailed car specification tracking (including performance and dimensions), and a complete workflow for customer engagement through test drives and order processing.

## Architecture

The project follows a modular monolithic architecture built with:
- **Backend**: Spring Boot 3.x
- **Database**: MS SQL Server
- **Security**: Spring Security with JWT (JSON Web Tokens)
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)

### Core Modules
- `auth`: User authentication and JWT handling.
- `car`: Inventory management, specifications, and image handling.
- `dealer`: Hierarchical dealer information and commission rates.
- `order`: Sales order processing, payment tracking, and activity logging.
- `testdrive`: Slot scheduling and customer booking for vehicle trials.
- `warehouse`: Inventory logistics and stock transfers.
- `user`: User management and role-based access control.

## Features

- **Hierarchical Dealer Management**: Supports various levels (e.g., Level 1, 2, 3) with configurable commission rates.
- **Detailed Car Inventory**: Tracks VIN numbers, engine numbers, dimensions, performance specs, and multiple high-resolution images.
- **Dynamic Price Programs**: Manage sales campaigns with effective dates, suggested pricing, and special color premiums.
- **Test Drive Scheduling**: Administers available slots and manages customer bookings.
- **Sales & Order Processing**: Handles the full order lifecycle from PENDING to APPROVED, including payment status tracking.
- **PDF Generation**: Automated generation of contracts and quotations using iText.
- **Warehouse Logistics**: Manages stock levels and tracks vehicle transfers between locations.

## Tech Stack

- **Java**: 17
- **Framework**: Spring Boot 3.5.6
- **Persistence**: Spring Data JPA (Hibernate)
- **Database**: MS SQL Server (mssql-jdbc)
- **Security**: Spring Security, JJWT (io.jsonwebtoken)
- **Documentation**: SpringDoc OpenAPI 2.8.13
- **Utilities**: Lombok, iText (PDF), Jakarta Validation

## Prerequisites

- **Java Development Kit (JDK) 17**
- **Apache Maven 3.x**
- **MS SQL Server** (Local or via Docker)

## Installation / Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd electric-vehicle-dealer-management-system
   ```

2. **Database Setup**:
   - Create a database named `evdealer_db`.
   - Execute the initialization script located at `sql/init.sql` to set up tables and sample data.

3. **Configure Environment Variables**:
   - Create a `.env` file or update `env_dev.txt` / `env_prod.txt` with your database credentials and JWT secret:
     ```env
     MSSQL_HOST=localhost
     MSSQL_PORT=1433
     MSSQL_DB=evdealer_db
     MSSQL_SA_USERNAME=sa
     MSSQL_SA_PASSWORD=YourPassword
     JWT_SECRET=YourSuperSecretKey
     ```

4. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Project Structure

```text
src/main/java/com/evdealer/ev_dealer_management
├── auth/           # Security & JWT Logic
├── car/            # Car models, specs & images
├── common/         # Shared utilities (exceptions, DTOs)
├── config/         # App configuration (CORS, Security)
├── order/          # Order & Payment logic
├── sale/           # Price programs & campaigns
├── testdrive/      # Slots & Booking management
├── user/           # User & Dealer staff management
└── warehouse/      # Inventory & Logistics
```

## Development Guide

### API Documentation
Once the application is running, you can access the Swagger UI to explore and test the APIs:
`http://localhost:8000/evdealer/swagger-ui/index.html`

### Logging & Debugging
- Default port: `8000` (Context path: `/evdealer`)
- DevTools are enabled for automatic restart during development.
- PDF uploads and thumbnails are stored in the `/uploads` directory.

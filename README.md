# JB Food Ordering System

A console-based food ordering application built with Java, implementing N-layered architecture and comprehensive testing.

## 🚀 Features

- **User Management**: Customer registration, login, and profile management
- **Food Management**: Admin CRUD operations for menu items (Register, Edit, Delete, View)
- **Order Processing**: Browse menu, place orders, and process payments
- **Payment Methods**: Multiple payment options (TNG, Cash, Online Banking)
- **Admin Panel**: Food management and order reports

## 🛠️ Tech Stack

- **Java 17**
- **Maven** - Build automation
- **JUnit 5** - Testing framework
- **JaCoCo** - Code coverage
- **Mockito** - Mocking framework
- **H2 Database** - In-memory database for testing
- **MySQL** - Production database

## 📦 Project Structure

```
src/
├── main/java/
│   ├── config/          # Database configuration
│   ├── controller/      # Controllers
│   ├── model/           # Domain models
│   ├── presentation/    # UI layer
│   │   ├── Admin/       # Admin handlers
│   │   ├── Customer/    # Customer handlers
│   │   ├── Food/        # Food module (99% coverage)
│   │   ├── Order/       # Order handlers
│   │   └── Payment/     # Payment handlers
│   ├── repository/      # Data access layer
│   └── service/         # Business logic layer
└── test/java/           # Unit tests (485 tests)
```

## 🏗️ Architecture

**N-Layered Architecture:**
- **Presentation** → **Controller** → **Service** → **Repository**
- Each layer has single responsibility
- Dependency injection for testability
- Repository pattern for data access


## 🚦 Running the Application

### Prerequisites
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Setup Database
```sql
CREATE DATABASE bmse3014_food_ordering;
```

### Run Application
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="presentation.Main"
```

## 🧪 Running Tests

```bash
# Run all tests
mvn clean test

# Run specific test class
mvn test -Dtest=FoodHandlerTest

# Generate coverage report
mvn jacoco:report
```


## 📋 Key Design Patterns

- **N-Layered Architecture** - Presentation → Controller → Service → Repository
- **Repository Pattern** - Data access abstraction with interfaces
- **Dependency Injection** - Constructor injection for testability
- **Enum Pattern** - Type-safe menu options (AdminMenuOption, FoodEditOption, etc.)
- **Exception Handling Pattern** - Custom UserCancelledException

## 🎯 Code Quality

- **SOLID Principles** applied throughout
- **DRY** (Don't Repeat Yourself) - Helper methods
- **KISS** (Keep It Simple) - Clean, readable code
- **Enum-based Menus** - Type-safe menu options
- **Exception Handling** - UserCancelledException for graceful exits
- **Input Validation** - Comprehensive validation with retry logic

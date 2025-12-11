# JB Food Ordering System

A console-based food ordering application built with Java, implementing N-layered architecture and comprehensive testing.

## 🚀 Features

- **User Management**: Customer registration, login, and profile management
- **Food Management**: Admin CRUD operations for menu items (Register, Edit, Delete, View)
- **Order Processing**: Browse menu, place orders, and process payments
- **Payment Methods**: Multiple payment options (TNG, Cash, Online Banking)
- **Admin Panel**: Food management and order reports
- **User Cancellation**: 'X' to exit functionality across all inputs

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

## 🧪 Testing & Coverage

```
Total Tests: 485
✅ Failures: 0
✅ Errors: 0
✅ Coverage: >85% (exceeds requirements)
```

### Module Coverage:
- **Food Module**: 99% (69 tests)
- **Repository Layer**: >85%
- **Service Layer**: >90%
- **Controller Layer**: >85%
- **Presentation Layer**: >85%

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

View coverage report: `target/site/jacoco/index.html`

## 👤 Default Users

**Admin:**
- Username: `admin`
- Password: `admin123`

**Customer (Test):**
- Register new account or use existing credentials

## 📋 Key Design Patterns

- **Repository Pattern** - Data access abstraction
- **Singleton Pattern** - Database connection
- **Factory Pattern** - Object creation
- **MVC Pattern** - Separation of concerns
- **Dependency Injection** - Loose coupling

## 🎯 Code Quality

- **SOLID Principles** applied throughout
- **DRY** (Don't Repeat Yourself) - Helper methods
- **KISS** (Keep It Simple) - Clean, readable code
- **Enum-based Menus** - Type-safe menu options
- **Exception Handling** - UserCancelledException for graceful exits
- **Input Validation** - Comprehensive validation with retry logic

## 📝 Assignment Requirements

✅ N-layered architecture  
✅ Test coverage >85%  
✅ Clean code principles  
✅ Repository pattern  
✅ Comprehensive documentation  
✅ Error handling  
✅ Input validation  

## 🤝 Contributors

BMSE3014 Assignment - Food Ordering System

## 📄 License

Academic Project - BMSE3014

---

**Note:** This is a console-based application developed for educational purposes.

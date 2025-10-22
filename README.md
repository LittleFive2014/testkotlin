# Kotlin Spring Boot Authentication System

A modern authentication system built with Kotlin, Spring Boot 3.4.0, Spring Security, and PostgreSQL. This project demonstrates secure user authentication, role-based access control, and password encryption using BCrypt.

## 🚀 Features

- ✅ User registration and authentication
- ✅ Secure password hashing with BCrypt
- ✅ Role-based access control (USER, ADMIN)
- ✅ Spring Security integration
- ✅ PostgreSQL database integration with JPA/Hibernate
- ✅ Thymeleaf template engine for server-side rendering
- ✅ RESTful API endpoints
- ✅ Comprehensive unit tests with JUnit 5
- ✅ Gradle 8.11.1 (Gradle 9.0 ready)

## 📋 Prerequisites

- **Java 21** (GraalVM or Oracle JDK)
- **Gradle 8.11.1** or higher
- **PostgreSQL** database server
- **Kotlin 2.2.20**

## 🛠️ Tech Stack

### Backend
- **Kotlin** 2.2.20
- **Spring Boot** 3.4.0
- **Spring Security** 6.x
- **Spring Data JPA**
- **Hibernate**

### Database
- **PostgreSQL** (JDBC Driver included)

### Frontend
- **Thymeleaf** (Template Engine)
- **Thymeleaf Spring Security 6**
- **HTML/CSS**

### Testing
- **JUnit 5** (Jupiter)
- **Spring Boot Test**
- **Spring Security Test**

### Build Tool
- **Gradle** 8.11.1 with Kotlin DSL

## 📁 Project Structure

```
testkotlin/
├── src/
│   ├── main/
│   │   ├── kotlin/com/example/auth/
│   │   │   ├── Application.kt              # Main application entry point
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.kt       # Spring Security configuration
│   │   │   ├── controller/
│   │   │   │   └── AuthController.kt       # Authentication endpoints
│   │   │   ├── dto/                        # Data Transfer Objects
│   │   │   ├── entity/
│   │   │   │   ├── User.kt                 # User entity
│   │   │   │   └── Role.kt                 # User roles enum
│   │   │   ├── repository/                 # JPA repositories
│   │   │   └── service/
│   │   │       ├── AuthService.kt          # Authentication service
│   │   │       └── PasswordService.kt      # Password encryption service
│   │   └── resources/
│   │       ├── application.yml             # Application configuration
│   │       ├── static/css/
│   │       │   └── styles.css              # CSS styles
│   │       └── templates/
│   │           ├── login.html              # Login page
│   │           ├── dashboard.html          # Dashboard page
│   │           └── fragments/              # Reusable fragments
│   │               ├── header.html
│   │               └── footer.html
│   └── test/
│       └── kotlin/com/example/auth/
│           └── service/
│               └── PasswordServiceTest.kt  # Unit tests
├── build.gradle.kts                        # Gradle build configuration
├── settings.gradle.kts                     # Gradle settings
└── README.md                               # This file
```

## ⚙️ Configuration

### Database Configuration

Update `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/auth_system
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver

  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8080
```

### Create Database

```sql
CREATE DATABASE auth_system;
```

The application will automatically create the necessary tables on startup (using `ddl-auto: update`).

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd testkotlin
```

### 2. Configure Database

Edit `src/main/resources/application.yml` with your PostgreSQL credentials.

### 3. Build the Project

```bash
./gradlew clean build
```

### 4. Run Tests

```bash
./gradlew test
```

View test reports at: `build/reports/tests/test/index.html`

### 5. Run the Application

```bash
./gradlew bootRun
```

Or run the JAR file:

```bash
java -jar build/libs/testkotlin-1.0-SNAPSHOT.jar
```

The application will start at: `http://localhost:8080`

## 📝 API Endpoints

### Authentication

- **POST** `/api/auth/register` - Register a new user
- **POST** `/api/auth/login` - User login
- **GET** `/api/auth/logout` - User logout

### Web Pages

- **GET** `/login` - Login page
- **GET** `/dashboard` - Dashboard (requires authentication)

## 🧪 Testing

The project includes comprehensive unit tests for the password service:

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests "com.example.auth.service.PasswordServiceTest"

# Run with detailed output
./gradlew test --info

# Generate test coverage report
./gradlew test jacocoTestReport
```

### Test Coverage

- ✅ Password encoding tests
- ✅ Password matching tests
- ✅ Edge case handling
- ✅ Integration tests

## 🏗️ Build Commands

```bash
# Clean build artifacts
./gradlew clean

# Compile Kotlin code
./gradlew compileKotlin

# Build without tests
./gradlew build -x test

# Build and run
./gradlew bootRun

# Check for dependency updates
./gradlew dependencyUpdates

# View Gradle version
./gradlew --version
```

## 🔒 Security Features

### Password Security
- **BCrypt** password hashing with automatic salt generation
- Configurable password strength requirements
- Protection against timing attacks

### Authentication
- Session-based authentication
- CSRF protection enabled
- Secure cookie handling
- Session timeout: 30 minutes

### Authorization
- Role-based access control (RBAC)
- Method-level security
- URL-based security rules

## 📦 Dependencies

### Core Dependencies
```kotlin
- Spring Boot Starter Web 3.4.0
- Spring Boot Starter Data JPA 3.4.0
- Spring Boot Starter Security 3.4.0
- Spring Boot Starter Thymeleaf 3.4.0
- Spring Boot Starter Validation 3.4.0
- Thymeleaf Extras Spring Security 6
- Kotlin Reflect 2.2.20
- Jackson Module Kotlin
- PostgreSQL Driver
```

### Development Dependencies
```kotlin
- Spring Boot DevTools
```

### Test Dependencies
```kotlin
- Spring Boot Starter Test
- Spring Security Test
- JUnit Platform Launcher
```

## 🔧 Gradle Configuration

- **Gradle Version**: 8.11.1
- **Kotlin Version**: 2.2.20
- **Java Version**: 21
- **JVM Toolchain**: Java 21
- **Build System**: Gradle Kotlin DSL

## 📈 Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

### Roles Enum
- `USER` - Standard user role
- `ADMIN` - Administrator role

## 🐛 Troubleshooting

### Database Connection Issues

```bash
# Check PostgreSQL is running
psql -U postgres -l

# Verify connection
psql -U postgres -d auth_system
```

### Gradle Issues

```bash
# Clean Gradle cache
rm -rf ~/.gradle/caches/

# Refresh dependencies
./gradlew --refresh-dependencies
```

### Port Already in Use

Change the port in `application.yml`:
```yaml
server:
  port: 8081
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)

## 📄 License

This project is open source and available under the MIT License.

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📧 Contact

For questions or support, please open an issue in the repository.

---

**Built with ❤️ using Kotlin and Spring Boot**


# SIJD Inventory Management System - Backend API

A robust Spring Boot REST API for inventory management with JWT authentication, real-time stock tracking, and comprehensive CRUD operations.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Team](#team)
- [Project Setup](#project-setup)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Authentication & Security](#authentication--security)
- [Testing](#testing)
- [Contributing](#contributing)

## 🚀 Project Overview

SIJD Inventory Management System Backend is a RESTful API built with Spring Boot that provides comprehensive inventory management capabilities. The system features JWT-based authentication, real-time stock tracking, and complete CRUD operations for inventory management.

### Key Capabilities
- 🔐 JWT-based authentication and authorization
- 📦 Real-time inventory tracking and management
- 📊 Complete stock in/out transaction management
- 🔍 Advanced search and filtering capabilities
- 📄 Paginated data retrieval for optimal performance
- 🔧 Comprehensive CRUD operations with validation
- 📈 Audit trail for all transactions

## ✨ Features

### Core Backend Features
- ✅ **JWT Authentication** - Secure login with access and refresh tokens
- ✅ **User Management** - Registration and user profile management
- ✅ **Stock Operations** - Complete stock in/out management
- ✅ **Transaction History** - Full audit trail of all stock movements
- ✅ **Search & Filter** - Advanced item search capabilities
- ✅ **Data Validation** - Comprehensive input validation and error handling
- ✅ **Pagination** - Optimized data retrieval for large datasets

### Advanced Features
- 🔄 **Real-time Updates** - Live inventory status tracking
- 📊 **Business Logic** - Automatic stock calculations and validations
- 🛡️ **Security** - Role-based access control with Spring Security
- 📝 **Audit Logging** - Complete transaction logging
- ⚡ **Performance** - Optimized queries and database indexing
- 🔧 **API Documentation** - Swagger/OpenAPI integration

## 🛠 Technology Stack

### Backend Framework & Libraries
- **Framework**: Spring Boot 2.7+
- **Language**: Java 11+
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA (Hibernate)
- **Validation**: Bean Validation (JSR-303)
- **Documentation**: Swagger/OpenAPI 3
- **Build Tool**: Maven 3.6+

### Development & Database Tools
- **IDE**: IntelliJ IDEA
- **Database Management**: MySQL Workbench
- **Version Control**: Git & GitHub
- **API Testing**: Postman
- **Dependency Management**: Maven

## 👥 Team

### Backend Development Team

#### **Ishara Umesh** - Lead Backend Developer & Project Manager  & Devops
- **GitHub**: [@ishu6285](https://github.com/ishu6285)
- **Role**: Lead Developer & GitHub Repository Manager
- **Backend Responsibilities**:
  - 🔐 **Authentication System**
    - User registration API (`/auth/user/register`)
    - User login API (`/auth/user/login`) 
    - JWT token refresh API (`/auth/user/refresh/token`)
    - JWT token generation and validation
    - Spring Security configuration
  - 📦 **Stock In Management**
    - Stock In API (`/admin/stock/in`)
    - Stock validation and business logic
    - Stock In history API (`/admin/get/all/stock-in`)
  - 📊 **Data Retrieval APIs**
    - Current stock retrieval (`/admin/get/all/current-stock`)
    - Pagination and sorting implementation
    - Item search API (`/admin/item/search/{query}`)
  - 📤 **Stock Out Management**
    - Stock Out API (`/admin/stock/out`)
    - Stock availability validation
    - Business logic for stock deduction
  - 🏗️ **Project Management**
    - Project architecture and setup
    - Database design and configuration
    - GitHub repository management and merges
    - Code review and integration
    - Documentation and API specifications

#### **Shakthi Shavisanka** - Backend Developer
- **GitHub**: [@dshakthi99](https://github.com/dshakthi99)
- **Role**: Backend API Developer
- **Backend Responsibilities**:
  - 📋 **Transaction History APIs**
    - Stock Out history API (`/admin/get/all/stock-out`)
    - Pagination for transaction records
  - ✏️ **Edit Operations**
    - Edit Stock In API (`/admin/stock/in/edit`)
    - Edit Stock Out API (`/admin/stock/out/edit`)
    - Stock recalculation logic for edits
  - 🗑️ **Delete Operations**
    - Delete Stock In API (`/admin/stock/in/delete/{id}`)
    - Delete Stock Out API (`/admin/stock/out/delete/{id}`)
    - Stock adjustment logic for deletions
  - 🔧 **Data Validation & Business Logic**
    - Input validation for all CRUD operations
    - Error handling and exception management
    - Performance optimization for database queries

## 🚀 Project Setup

### Prerequisites
- **Java**: JDK 11 or higher
- **Maven**: 3.6+ 
- **MySQL**: 8.0+
- **IntelliJ IDEA**: Latest version (recommended)
- **Git**: Latest version
- **Postman**: For API testing

### IntelliJ IDEA Setup Guide

#### 1. Clone Repository
```bash
git clone https://github.com/ishu6285/sijd-inventory-management.git
cd sijd-inventory-management
```

#### 2. Import Project in IntelliJ IDEA
1. Open **IntelliJ IDEA**
2. Click **"Open or Import"**
3. Select the project root directory
4. Choose **"Import project from external model"** → **"Maven"**
5. Click **"Next"** → **"Next"** → **"Finish"**
6. Wait for Maven to download dependencies

#### 3. Configure Project Settings
1. **File** → **Project Structure** (Ctrl+Alt+Shift+S)
2. **Project Settings** → **Project**
3. Set **Project SDK**: Java 11+
4. Set **Project language level**: 11
5. Apply changes

#### 4. Database Setup
Create MySQL database:
```sql
CREATE DATABASE sijd_inventory;
CREATE USER 'sijd_user'@'localhost' IDENTIFIED BY 'sijd_password';
GRANT ALL PRIVILEGES ON sijd_inventory.* TO 'sijd_user'@'localhost';
FLUSH PRIVILEGES;
```

#### 5. Application Configuration
Create/Update `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/sijd

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/sijd_inventory
spring.datasource.username=sijd_user
spring.datasource.password=sijd_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JWT Configuration
jwt.secret=sijd-inventory-secret-key-2024
jwt.expiration=86400000
jwt.refresh.expiration=604800000

# Logging Configuration
logging.level.com.sijd=DEBUG
logging.level.org.springframework.security=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

#### 6. Run Application
1. Open `SijdInventoryApplication.java`
2. Right-click → **"Run 'SijdInventoryApplication'"**
3. Or click the green play button
4. Application starts on: `http://localhost:8080/sijd`

#### 7. Verify Setup
- **Health Check**: `GET http://localhost:8080/sijd/api/v1/health`
- **Swagger UI**: `http://localhost:8080/sijd/swagger-ui.html`

### Maven Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package JAR
mvn clean package

# Run application
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring.profiles.active=dev
```

### Development Profiles

#### Development Profile (`application-dev.properties`)
```properties
spring.profiles.active=dev
spring.jpa.show-sql=true
logging.level.com.sijd=DEBUG
spring.jpa.hibernate.ddl-auto=update
```

#### Production Profile (`application-prod.properties`)
```properties
spring.profiles.active=prod
spring.jpa.show-sql=false
logging.level.com.sijd=INFO
spring.jpa.hibernate.ddl-auto=validate
```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/sijd/api/v1
```

### Authentication Endpoints
> **Developer**: Ishara Umesh [@ishu6285](https://github.com/ishu6285)

#### 1. User Registration
- **Endpoint**: `POST /auth/user/register`
- **Description**: Register a new user in the system
- **Access**: Public

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe", 
  "middleName": "A",
  "userType": "ADMIN",
  "email": "john.doe@example.com",
  "password": "password123",
  "nickName": "Johnny"
}
```

**Response:**
```json
{
  "message": "Success",
  "data": {
    "user": {
      "userId": 1,
      "firstName": "John",
      "lastName": "Doe",
      "userType": "ADMIN",
      "email": "john.doe@example.com",
      "isActive": true,
      "effectiveDate": "2025-06-20T10:30:00"
    },
    "message": "user registration success"
  }
}
```

#### 2. User Login
- **Endpoint**: `POST /auth/user/login`
- **Description**: Authenticate user and receive JWT tokens
- **Access**: Public

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "message": "Success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expirationTime": "24h",
    "statusCode": 200,
    "message": "login success",
    "role": "ADMIN"
  }
}
```

#### 3. Refresh Token
- **Endpoint**: `POST /auth/user/refresh/token`
- **Description**: Refresh JWT access token using refresh token
- **Access**: Public

**Request Body:**
```json
{
  "token": "refresh_token_here"
}
```

### Stock Management Endpoints
> **Authentication Required**: Bearer Token in Authorization header

#### Stock In Operations
> **Developer**: Ishara Umesh [@ishu6285](https://github.com/ishu6285)

##### Add Stock
- **Endpoint**: `POST /admin/stock/in`
- **Description**: Add stock for an item (creates item if doesn't exist)
- **Access**: ADMIN only

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "itemName": "Laptop",
  "quantity": 10.50
}
```

**Response:**
```json
"Stock in success for item 'Laptop'. Added quantity: 10.50. New total: 25.75"
```

#### Stock Out Operations
> **Developer**: Shakthi Shavisanka [@dshakthi99](https://github.com/dshakthi99)

##### Remove Stock
- **Endpoint**: `POST /admin/stock/out`
- **Description**: Remove stock for an item with availability validation
- **Access**: ADMIN only

**Request Body:**
```json
{
  "itemName": "Laptop", 
  "quantity": 2.00
}
```

**Response:**
```json
"Stock out success for item 'Laptop'. Quantity removed: 2.00. Remaining stock: 23.75"
```

#### Edit Operations
> **Developer**: Shakthi Shavisanka [@dshakthi99](https://github.com/dshakthi99)

##### Update Stock In Record
- **Endpoint**: `PUT /admin/stock/in/edit`
- **Description**: Update existing stock-in record and recalculate current stock

**Request Body:**
```json
{
  "stockInId": 123,
  "itemName": "Laptop",
  "quantity": 15.00
}
```

##### Update Stock Out Record
- **Endpoint**: `PUT /admin/stock/out/edit` 
- **Description**: Update existing stock-out record and recalculate current stock

**Request Body:**
```json
{
  "stockOutId": 456,
  "itemName": "Laptop",
  "quantity": 3.00
}
```

#### Delete Operations
> **Developer**: Shakthi Shavisanka [@dshakthi99](https://github.com/dshakthi99)

##### Delete Stock In Record
- **Endpoint**: `DELETE /admin/stock/in/delete/{stockInId}`
- **Description**: Delete stock-in record and adjust current stock

**Path Parameters:**
- `stockInId`: Long - ID of stock-in record

##### Delete Stock Out Record
- **Endpoint**: `DELETE /admin/stock/out/delete/{stockOutId}`
- **Description**: Delete stock-out record and adjust current stock

**Path Parameters:**
- `stockOutId`: Long - ID of stock-out record

### Data Retrieval Endpoints

#### Current Stock
> **Developer**: Ishara Umesh [@ishu6285](https://github.com/ishu6285)

##### Get All Current Stock
- **Endpoint**: `GET /admin/get/all/current-stock`
- **Description**: Retrieve paginated current stock with sorting

**Query Parameters:**
- `page`: Integer (default: 0) - Page number
- `size`: Integer (default: 10) - Items per page  
- `sortField`: String (default: "createdDateTime") - Sort field
- `sortDirection`: String (default: "desc") - Sort direction

**Example:**
```
GET /admin/get/all/current-stock?page=0&size=5&sortField=itemName&sortDirection=asc
```

#### Transaction History
> **Developer**: Shakthi Shavisanka [@dshakthi99](https://github.com/dshakthi99)

##### Get Stock In History
- **Endpoint**: `GET /admin/get/all/stock-in`
- **Description**: Retrieve paginated stock-in transaction history
- **Query Parameters**: Same as current stock

##### Get Stock Out History  
- **Endpoint**: `GET /admin/get/all/stock-out`
- **Description**: Retrieve paginated stock-out transaction history
- **Query Parameters**: Same as current stock

#### Search
> **Developer**: Ishara Umesh [@ishu6285](https://github.com/ishu6285)

##### Search Items
- **Endpoint**: `GET /admin/item/search/{searchQuery}`
- **Description**: Search items by name with partial matching

**Path Parameters:**
- `searchQuery`: String - Search term

**Example:**
```
GET /admin/item/search/Lap
```

**Response:**
```json
[
  "Laptop",
  "Laptop Charger", 
  "Laptop Bag"
]
```

### Standard Response Format

#### Success Response
```json
{
  "message": "Success",
  "data": {
    // Response data object
  }
}
```

#### Error Response
```json
{
  "message": "Error description",
  "status": 400,
  "timestamp": "2025-06-20T10:30:00",
  "path": "/api/v1/endpoint"
}
```

#### Validation Error Response
```json
{
  "fieldName": {
    "value": "invalid_value",
    "description": "Validation error message"
  }
}
```

## 🗄 Database Schema

### Users Table
```sql
CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL, 
    middle_name VARCHAR(100),
    user_type ENUM('ADMIN', 'USER') NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    effective_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    nick_name VARCHAR(100),
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Item Current Table
```sql
CREATE TABLE item_current (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL UNIQUE,
    item_quantity DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    version INT DEFAULT 1,
    created_user VARCHAR(255),
    created_date_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_user VARCHAR(255),
    modified_date_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    INDEX idx_item_name (item_name),
    INDEX idx_created_date (created_date_time)
);
```

### Stock In Table
```sql
CREATE TABLE stock_in (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    item_quantity DECIMAL(10,2) NOT NULL,
    version INT DEFAULT 1,
    created_user VARCHAR(255),
    created_date_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_user VARCHAR(255),
    modified_date_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    INDEX idx_item_name (item_name),
    INDEX idx_created_date (created_date_time),
    INDEX idx_created_user (created_user)
);
```

### Stock Out Table
```sql
CREATE TABLE stock_out (
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(255) NOT NULL,
    item_quantity DECIMAL(10,2) NOT NULL,
    version INT DEFAULT 1,
    created_user VARCHAR(255),
    created_date_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_user VARCHAR(255),
    modified_date_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    INDEX idx_item_name (item_name),
    INDEX idx_created_date (created_date_time),
    INDEX idx_created_user (created_user)
);
```

## 🔐 Authentication & Security

### JWT Configuration
- **Access Token Expiry**: 24 hours (86400000ms)
- **Refresh Token Expiry**: 7 days (604800000ms)
- **Algorithm**: HMAC SHA-256 (HS256)
- **Secret Key**: Configurable in application.properties

### Security Features
- **Password Encryption**: BCrypt with strength 12
- **Role-Based Access**: ADMIN/USER roles
- **CORS Support**: Configured for cross-origin requests
- **Request Validation**: Bean validation on all inputs
- **SQL Injection Protection**: JPA parameterized queries
- **XSS Protection**: Input sanitization

### Security Flow
1. User registers with encrypted password
2. User logs in with credentials
3. Server validates and generates JWT tokens
4. Client includes token in Authorization header
5. Server validates token on each request
6. Automatic token refresh before expiry

### Protected Endpoints
All `/admin/*` endpoints require:
- Valid JWT token in Authorization header
- ADMIN role privileges
- Active user status

## 🧪 Testing

### Unit Testing
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run with coverage
mvn test jacoco:report
```

### Integration Testing
```bash
# Run integration tests
mvn verify

# Run with specific profile
mvn verify -Dspring.profiles.active=test
```

### API Testing with Postman

#### Setup Postman Environment
```json
{
  "baseUrl": "http://localhost:8080/sijd",
  "version": "v1",
  "authToken": "",
  "refreshToken": ""
}
```

#### Test Sequence
1. **User Registration** → Get user created
2. **User Login** → Get access & refresh tokens  
3. **Stock In** → Add initial stock
4. **Stock Out** → Remove some stock
5. **Get Current Stock** → Verify quantities
6. **Edit Operations** → Update records
7. **Delete Operations** → Remove records
8. **Search Items** → Test search functionality

### Test Data Setup
```sql
-- Insert test user
INSERT INTO users (first_name, last_name, user_type, email, password, nick_name) 
VALUES ('Admin', 'User', 'ADMIN', 'admin@sijd.com', '$2a$12$encrypted_password', 'Admin');

-- Insert test items
INSERT INTO item_current (item_name, item_quantity, created_user, status) 
VALUES 
('Test Laptop', 25.00, 'admin@sijd.com', 'ACTIVE'),
('Test Mouse', 50.00, 'admin@sijd.com', 'ACTIVE');
```

## 🤝 Contributing

### Development Workflow

#### For Team Members

**Ishara Umesh** (Project Manager):
- Manages main branch and all merges
- Reviews all pull requests
- Handles project setup and configuration
- Coordinates development activities

**Shakthi Shavisanka**:
- Creates feature branches for assigned tasks
- Implements backend functionality
- Submits pull requests for review

#### Branch Strategy
```bash
# Main branch (protected)
main

# Feature branches
feature/authentication-system      # Ishara
feature/stock-in-operations       # Ishara  
feature/stock-out-operations      # Shakthi
feature/edit-delete-operations    # Shakthi
feature/history-apis              # Shakthi
```

#### Development Process
1. **Create Feature Branch**
```bash
git checkout -b feature/feature-name
```

2. **Implement Changes**
- Follow Java coding standards
- Add comprehensive JavaDoc comments
- Include unit tests for new functionality
- Update API documentation

3. **Test Implementation**
```bash
mvn clean test
mvn spring-boot:run
# Test with Postman
```

4. **Commit Changes**
```bash
git add .
git commit -m "feat: implement feature description"
```

5. **Push Branch**
```bash
git push origin feature/feature-name
```

6. **Create Pull Request**
- Submit PR to main branch
- Add detailed description
- Include test results
- Request review from Ishara Umesh

### Code Standards

#### Java Conventions
```java
// Class naming: PascalCase
public class UserService {
    
    // Method naming: camelCase with descriptive names
    public ResponseEntity<UserDto> registerNewUser(UserRegistrationRequest request) {
        // Implementation
    }
    
    // Variable naming: camelCase
    private final UserRepository userRepository;
    
    // Constants: UPPER_SNAKE_CASE
    private static final String DEFAULT_USER_ROLE = "USER";
}
```

#### Documentation Standards
```java
/**
 * Service class for managing user operations including registration and authentication.
 * 
 * @author Ishara Umesh
 * @version 1.0
 * @since 2024-06-20
 */
@Service
public class UserService {
    
    /**
     * Registers a new user in the system with encrypted password.
     * 
     * @param request User registration details
     * @return ResponseEntity containing user data or error message
     * @throws UserAlreadyExistsException if email already exists
     */
    public ResponseEntity<UserDto> registerUser(UserRegistrationRequest request) {
        // Implementation
    }
}
```

### Commit Message Format
```
type(scope): description

feat(auth): implement JWT token refresh functionality
fix(stock): resolve stock calculation issue in edit operation  
docs(api): update endpoint documentation
test(user): add unit tests for user service
refactor(stock): optimize stock retrieval query performance
```

### Pull Request Template
```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature  
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed
- [ ] Postman collection updated

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No merge conflicts
```

## 📊 Performance & Monitoring

### Database Optimization
- **Indexing**: Applied on frequently queried columns
- **Connection Pooling**: HikariCP configuration
- **Query Optimization**: JPA query tuning
- **Pagination**: Prevents large data loads

### Application Monitoring
```properties
# Actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always

# Performance monitoring
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Performance Metrics
- API response time < 200ms
- Database query time < 50ms  
- Memory usage optimization
- CPU usage monitoring

## 🔧 Troubleshooting

### Common Development Issues

#### Database Connection Failed
```bash
# Check MySQL service
sudo systemctl status mysql

# Verify database exists
mysql -u root -p
USE sijd_inventory;
SHOW TABLES;
```

#### Port 8080 Already in Use
```bash
# Find process using port
lsof -i :8080
netstat -tulpn | grep :8080

# Kill process
kill -9 <PID>
```

#### JWT Token Issues
- Verify secret key configuration
- Check token expiration time
- Ensure proper Authorization header format
- Validate token generation logic

#### Build Issues
```bash
# Clean Maven cache
mvn clean

# Re-download dependencies  
mvn dependency:resolve

# Rebuild project
mvn clean compile
```

### Logging Configuration
```properties
# Application logs
logging.file.name=logs/sijd-inventory.log
logging.level.com.sijd=DEBUG
logging.level.org.springframework.security=DEBUG

# SQL logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type=TRACE
```

### Health Check Endpoint
```bash
# Application health
curl http://localhost:8080/sijd/actuator/health

# Database connectivity
curl http://localhost:8080/sijd/actuator/health/db
```

## 📞 Support & Contact

### Technical Support
- **Lead Developer**: Ishara Umesh - [@ishu6285](https://github.com/ishu6285)
- **Backend Developer**: Shakthi Shavisanka - [@dshakthi99](https://github.com/dshakthi99)

### Project Resources
- **Repository**: [GitHub Repository](https://github.com/ishu6285/sijd-inventory-management)
- **Issues**: [GitHub Issues](https://github.com/ishu6285/sijd-inventory-management/issues)
- **Wiki**: [Project Wiki](https://github.com/ishu6285/sijd-inventory-management/wiki)

### Getting Help
1. Check existing documentation
2. Search closed issues
3. Create new issue with:
   - Environment details
   - Steps to reproduce
   - Expected vs actual behavior
   - Error logs/screenshots

---

**Project Status**: ✅ Active Development  
**API Version**: v1.0  
**Last Updated**: June 2025  
**Backend Version**: 1.0.0

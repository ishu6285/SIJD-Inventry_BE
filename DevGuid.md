# SIJD Inventory Management System - Developer Guide

## Table of Contents
- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Database Design](#database-design)
- [Security Implementation](#security-implementation)
- [API Design](#api-design)
- [Business Logic](#business-logic)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [Code Standards](#code-standards)

## Project Overview

The SIJD Inventory Management System is a Spring Boot-based REST API application that provides comprehensive inventory management capabilities with JWT authentication, role-based access control, and complete audit trails.

### Key Features
- JWT-based authentication and authorization
- Role-based access control (ADMIN/USER)
- Complete inventory tracking with audit logs
- CRUD operations for stock management
- Pagination and sorting for data retrieval
- Search functionality
- Optimistic locking for concurrent updates
- Comprehensive error handling
- CORS configuration for cross-origin requests

## Architecture

### Layered Architecture
```
┌─────────────────────┐
│   Controller Layer  │  ← REST Controllers
├─────────────────────┤
│   Service Layer     │  ← Business Logic
├─────────────────────┤
│   Repository Layer  │  ← Data Access
├─────────────────────┤
│   Entity Layer      │  ← JPA Entities
└─────────────────────┘
```

### Key Components
- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic and validation
- **Repositories**: Data access layer using Spring Data JPA
- **Entities**: JPA entities with audit capabilities
- **DTOs**: Data transfer objects for API communication
- **Security**: JWT authentication and CORS configuration
- **Exception Handling**: Global exception handling with custom responses

## Technology Stack

### Core Technologies
- **Java**: 17+
- **Spring Boot**: 3.x
- **Spring Security**: JWT authentication
- **Spring Data JPA**: Data persistence
- **MySQL**: 8.0+ database
- **Maven**: Dependency management

### Dependencies
```xml
<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## Project Structure

```
src/
├── main/
│   ├── java/com/sijd/ims/
│   │   ├── SIJDInventoryBeApplication.java
│   │   ├── config/                    # Configuration classes
│   │   │   ├── AuditorAwareImpl.java
│   │   │   ├── CorsConfig.java
│   │   │   ├── JWTAuthFilter.java
│   │   │   ├── JpaAuditingConfig.java
│   │   │   ├── MessageConfig.java
│   │   │   └── SecurityConfig.java
│   │   ├── constant/                  # Application constants
│   │   │   └── AppConstant.java
│   │   ├── controller/                # REST Controllers
│   │   │   └── application/
│   │   │       ├── StockController.java
│   │   │       └── UserController.java
│   │   ├── dto/                       # Data Transfer Objects
│   │   │   ├── application/
│   │   │   └── user/
│   │   ├── entity/                    # JPA Entities
│   │   │   ├── BaseEntity.java
│   │   │   ├── AuditCreateUser.java
│   │   │   ├── AuditModifyUser.java
│   │   │   ├── application/
│   │   │   └── user/
│   │   ├── enums/                     # Enumerations
│   │   │   └── UserType.java
│   │   ├── exception/                 # Exception handling
│   │   │   ├── SijdException.java
│   │   │   └── SijdExceptionHandler.java
│   │   ├── repository/                # Data repositories
│   │   │   ├── application/
│   │   │   └── user/
│   │   ├── response/                  # Response DTOs
│   │   │   ├── APIResponse.java
│   │   │   └── AuthResponse.java
│   │   ├── service/                   # Service interfaces
│   │   │   └── application/
│   │   ├── service/impl/              # Service implementations
│   │   │   ├── AuthenticationService.java
│   │   │   ├── StockServiceImpl.java
│   │   │   └── UserDetailsService.java
│   │   └── utility/                   # Utility classes
│   │       └── JWTUtils.java
│   └── resources/
│       ├── application.properties
│       ├── messages.properties
│       └── *.postman_collection.json
```

## Setup and Installation

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Local Development Setup

1. **Clone the Repository**
```bash
git clone <repository-url>
cd sijd-inventory-management
```

2. **Database Setup**
```sql
CREATE DATABASE sijd_db;
CREATE USER 'sijd'@'localhost' IDENTIFIED BY 'sijd@123';
GRANT ALL PRIVILEGES ON sijd_db.* TO 'sijd'@'localhost';
FLUSH PRIVILEGES;
```

3. **Configure Database Connection**
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sijd_db?createDatabaseIfNotExist=true
spring.datasource.username=sijd
spring.datasource.password=sijd@123
```

4. **Build and Run**
```bash
mvn clean install
mvn spring-boot:run
```

5. **Verify Installation**
```bash
curl http://localhost:8080/sijd/api/v1/auth/user/register
```

### Environment Configuration

#### Development
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/sijd_db
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Logging
logging.level.com.sijd.ims=DEBUG
logging.level.org.springframework.security=DEBUG
```

#### Production
```properties
# Database
spring.datasource.url=jdbc:mysql://prod-server:3306/sijd_db
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# Security
jwt.secret=${JWT_SECRET}

# Logging
logging.level.root=WARN
logging.level.com.sijd.ims=INFO
```

## Database Design

### Entity Relationships
```
User (1) ──────────── (*) ItemCurrent
                           │
ItemStockIn (*) ──────────┘
ItemStockOut (*) ─────────┘
```

### Key Entities

#### BaseEntity
```java
@MappedSuperclass
public abstract class BaseEntity {
    @Version
    private int version;  // Optimistic locking
}
```

#### AuditCreateUser
```java
@MappedSuperclass
public abstract class AuditCreateUser extends BaseEntity {
    @CreatedBy
    private String createdUser;
    
    @CreatedDate
    private LocalDateTime createdDateTime;
}
```

#### AuditModifyUser
```java
@MappedSuperclass
public abstract class AuditModifyUser extends AuditCreateUser {
    @LastModifiedBy
    private String modifiedUser;
    
    @LastModifiedDate
    private LocalDateTime modifiedDateTime;
    
    private Status status;
}
```

### Database Schema

#### Tables
- `user`: User accounts and authentication
- `item_current`: Current inventory levels
- `item_stock_in`: Stock addition transactions
- `item_stock_out`: Stock removal transactions

#### Indexes
```sql
-- Performance indexes
CREATE INDEX idx_item_current_name ON item_current(item_name);
CREATE INDEX idx_stock_in_created ON item_stock_in(created_date_time);
CREATE INDEX idx_stock_out_created ON item_stock_out(created_date_time);
CREATE INDEX idx_user_email ON user(email);
```

## Security Implementation

### JWT Authentication Flow
```
1. User registers/logs in
2. Server validates credentials
3. Server generates JWT token
4. Client includes token in subsequent requests
5. Server validates token on each request
```

### Security Configuration
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated())
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

### JWT Token Structure
```json
{
  "header": {
    "alg": "HS256",
    "typ": "JWT"
  },
  "payload": {
    "sub": "user@example.com",
    "iat": 1640995200,
    "exp": 1641081600
  }
}
```

### Password Security
- BCrypt hashing with salt
- Minimum password requirements (to be implemented)
- Account lockout after failed attempts (to be implemented)

## API Design

### RESTful Principles
- Resource-based URLs
- HTTP methods for operations (GET, POST, PUT, DELETE)
- Stateless communication
- Standard HTTP status codes
- JSON request/response format

### Response Format
```java
@Data
@AllArgsConstructor
@Builder
public class APIResponse<T> {
    private String message;
    private T data;
    
    public static <T> APIResponse<T> success(T data) {
        return APIResponse.<T>builder()
            .data(data)
            .message("Success")
            .build();
    }
}
```

### Error Handling
```java
@RestControllerAdvice
public class SijdExceptionHandler {
    
    @ExceptionHandler(SijdException.class)
    public ResponseEntity<?> handleSijdException(SijdException ex) {
        return ResponseEntity
            .unprocessableEntity()
            .body(APIResponse.error(ex.getMessage()));
    }
}
```

### Validation
```java
public class StockRequest {
    @NotBlank(message = "Item name is required")
    @Size(min = 2, max = 100)
    private String itemName;
    
    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "999999.99")
    private BigDecimal quantity;
}
```

## Business Logic

### Stock Management Rules

#### Stock In Operations
1. If item exists: Add quantity to current stock
2. If item doesn't exist: Create new item with quantity
3. Record transaction in stock_in table
4. Update current stock table

#### Stock Out Operations
1. Validate item exists
2. Check sufficient quantity available
3. Subtract quantity from current stock
4. Record transaction in stock_out table
5. Prevent negative stock levels

#### Update Operations
1. Find existing transaction record
2. Calculate quantity difference
3. Adjust current stock accordingly
4. Validate business rules (no negative stock)
5. Update transaction record

#### Delete Operations
1. Find transaction record
2. Reverse the stock impact
3. Validate resulting stock levels
4. Delete transaction record

### Concurrency Control
```java
@Entity
public class ItemCurrent extends AuditModifyUser {
    @Version
    private int version;  // Optimistic locking
    
    // Prevents concurrent modification issues
}
```

### Transaction Management
```java
@Service
@Transactional
public class StockServiceImpl implements StockService {
    
    @Transactional
    public String saveStockIn(StockRequest request) {
        // All operations in single transaction
        // Rollback on any failure
    }
}
```

## Testing

### Unit Testing Structure
```
src/test/java/
├── controller/
│   ├── StockControllerTest.java
│   └── UserControllerTest.java
├── service/
│   ├── StockServiceTest.java
│   └── AuthenticationServiceTest.java
├── repository/
│   └── CurrentStockRepositoryTest.java
└── integration/
    └── StockManagementIntegrationTest.java
```

### Testing Examples

#### Unit Test Example
```java
@ExtendWith(MockitoExtension.class)
class StockServiceTest {
    
    @Mock
    private CurrentStockRepository currentStockRepository;
    
    @InjectMocks
    private StockServiceImpl stockService;
    
    @Test
    void saveStockIn_NewItem_ShouldCreateItem() {
        // Given
        StockRequest request = StockRequest.builder()
            .itemName("Test Item")
            .quantity(new BigDecimal("10.00"))
            .build();
            
        when(currentStockRepository.existsByItemName("Test Item"))
            .thenReturn(false);
            
        // When
        String result = stockService.saveStockIn(request);
        
        // Then
        assertThat(result).contains("New item 'Test Item' added");
        verify(currentStockRepository).save(any(ItemCurrent.class));
    }
}
```

#### Integration Test Example
```java
@SpringBootTest
@Transactional
class StockManagementIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void completeStockFlow_ShouldWorkCorrectly() {
        // 1. Register user
        // 2. Login and get token
        // 3. Add stock
        // 4. Remove stock
        // 5. Verify final quantities
    }
}
```

### Test Data Setup
```java
@TestConfiguration
public class TestDataConfig {
    
    @Bean
    @Primary
    public DataSource testDataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .build();
    }
}
```

## Deployment

### Docker Setup
```dockerfile
FROM openjdk:17-jre-slim

WORKDIR /app

COPY target/sijd-inventory-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/sijd_db
      - SPRING_DATASOURCE_USERNAME=sijd
      - SPRING_DATASOURCE_PASSWORD=sijd@123
    depends_on:
      - db
      
  db:
    image: mysql:8.0
    environment:
      - MYSQL_DATABASE=sijd_db
      - MYSQL_USER=sijd
      - MYSQL_PASSWORD=sijd@123
      - MYSQL_ROOT_PASSWORD=rootpass
    ports:
      - "3306:3306"
```

### Production Deployment Checklist
- [ ] Environment-specific configuration
- [ ] Database migration scripts
- [ ] SSL/TLS configuration
- [ ] Load balancer setup
- [ ] Monitoring and logging
- [ ] Backup strategy
- [ ] Security hardening
- [ ] Performance tuning

## Contributing

### Development Workflow
1. **Fork the repository**
2. **Create feature branch**: `git checkout -b feature/your-feature`
3. **Commit changes**: `git commit -am 'Add your feature'`
4. **Push to branch**: `git push origin feature/your-feature`
5. **Create Pull Request**

### Code Review Process
1. All changes require pull request
2. At least one reviewer approval
3. All tests must pass
4. Code coverage >= 80%
5. No security vulnerabilities

### Branching Strategy
```
main
├── develop
│   ├── feature/user-management
│   ├── feature/reporting
│   └── feature/notifications
├── release/v1.1.0
└── hotfix/security-patch
```

## Code Standards

### Java Code Style
```java
// Class naming: PascalCase
public class StockService {
    
    // Method naming: camelCase
    public String saveStockIn(StockRequest request) {
        
        // Variable naming: camelCase
        BigDecimal currentQuantity = getCurrentQuantity();
        
        // Constants: UPPER_SNAKE_CASE
        private static final String DEFAULT_STATUS = "ACTIVE";
    }
}
```

### Documentation Standards
- All public methods must have Javadoc
- Complex business logic requires inline comments
- README files for each major component
- API documentation using OpenAPI/Swagger (to be added)

### Testing Standards
- Unit tests for all service methods
- Integration tests for complete workflows
- Test coverage >= 80%
- Mock external dependencies
- Use meaningful test names

### Security Guidelines
- Never log sensitive information
- Validate all input parameters
- Use parameterized queries
- Implement proper error handling
- Regular security dependency updates

### Performance Guidelines
- Use pagination for large datasets
- Implement proper indexing
- Cache frequently accessed data
- Monitor query performance
- Use connection pooling

## Future Enhancements

### Planned Features
- [ ] Advanced reporting and analytics
- [ ] Batch import/export functionality
- [ ] Email notifications for low stock
- [ ] Multi-location inventory support
- [ ] Mobile app support
- [ ] Real-time stock updates via WebSocket
- [ ] Integration with external systems
- [ ] Advanced search and filtering

### Technical Improvements
- [ ] OpenAPI/Swagger documentation
- [ ] GraphQL

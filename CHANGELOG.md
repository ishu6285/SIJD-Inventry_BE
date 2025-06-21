# Changelog

All notable changes to the SIJD Inventory Management System will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Planned
- OpenAPI/Swagger documentation
- Redis caching implementation
- Email notifications for low stock alerts
- Batch import/export functionality
- Multi-location inventory support
- Real-time updates via WebSocket
- Advanced reporting and analytics
- Mobile app API endpoints

## [1.0.0] - 2024-01-15

### Added
- Initial release of SIJD Inventory Management System
- Complete JWT-based authentication system
- User registration and login functionality
- Role-based access control (ADMIN/USER roles)
- Stock management operations (stock in/out)
- Current inventory tracking
- Complete CRUD operations for stock records
- Pagination and sorting for all data retrieval endpoints
- Item search functionality with partial name matching
- Comprehensive audit trails for all operations
- Optimistic locking for concurrent updates
- Global exception handling with detailed error responses
- CORS configuration for cross-origin requests
- Comprehensive Postman collections for API testing

### Features

#### Authentication & Authorization
- JWT token-based authentication
- Refresh token mechanism (7-day expiration)
- BCrypt password hashing
- Role-based endpoint protection
- Automatic user auditing for all operations

#### Stock Management
- **Stock In Operations**
  - Add new items to inventory
  - Increase quantity for existing items
  - Automatic current stock updates
  - Transaction history recording

- **Stock Out Operations**
  - Remove items from inventory
  - Quantity validation (prevents negative stock)
  - Automatic current stock updates
  - Transaction history recording

- **Update Operations**
  - Modify existing stock-in records
  - Modify existing stock-out records
  - Automatic stock level recalculation
  - Business rule validation

- **Delete Operations**
  - Remove stock-in transactions
  - Remove stock-out transactions
  - Automatic stock level adjustment
  - Prevents operations that would result in negative stock

#### Data Management
- **Current Stock View**
  - Real-time inventory levels
  - Paginated data retrieval
  - Sortable by multiple fields
  - Complete audit information

- **Transaction History**
  - Complete stock-in transaction history
  - Complete stock-out transaction history
  - Detailed audit trails with user and timestamp information
  - Pagination and sorting support

- **Search Functionality**
  - Item search by partial name matching
  - Quick item lookup for operations
  - Case-insensitive search

#### Technical Features
- **Database Design**
  - MySQL 8.0+ support
  - JPA entities with inheritance hierarchy
  - Optimistic locking with version control
  - Automatic timestamp and user auditing
  - Foreign key relationships for data integrity

- **Error Handling**
  - Global exception handler
  - Detailed validation error responses
  - Business rule violation handling
  - Proper HTTP status codes
  - Nested JSON error structures for complex validation

- **Configuration**
  - Environment-specific configuration support
  - CORS configuration for frontend integration
  - JWT secret configuration
  - Database connection pooling
  - Configurable pagination defaults

### API Endpoints

#### Authentication Endpoints
- `POST /api/v1/auth/user/register` - User registration
- `POST /api/v1/auth/user/login` - User authentication
- `POST /api/v1/auth/user/refresh/token` - Token refresh

#### Stock Management Endpoints (ADMIN only)
- `POST /api/v1/admin/stock/in` - Add stock
- `POST /api/v1/admin/stock/out` - Remove stock
- `PUT /api/v1/admin/stock/in/edit` - Update stock-in record
- `PUT /api/v1/admin/stock/out/edit` - Update stock-out record
- `DELETE /api/v1/admin/stock/in/delete/{id}` - Delete stock-in record
- `DELETE /api/v1/admin/stock/out/delete/{id}` - Delete stock-out record

#### Data Retrieval Endpoints (ADMIN only)
- `GET /api/v1/admin/get/all/current-stock` - Get current inventory
- `GET /api/v1/admin/get/all/stock-in` - Get stock-in history
- `GET /api/v1/admin/get/all/stock-out` - Get stock-out history
- `GET /api/v1/admin/item/search/{query}` - Search items

### Database Schema

#### Tables Created
- `user` - User accounts and authentication data
- `item_current` - Current inventory levels
- `item_stock_in` - Stock addition transaction history
- `item_stock_out` - Stock removal transaction history

#### Key Features
- Automatic table creation with `spring.jpa.hibernate.ddl-auto=update`
- Foreign key constraints for data integrity
- Indexes for performance optimization
- Audit columns (created_user, created_date_time, modified_user, modified_date_time)
- Status tracking (ACTIVE/INACTIVE)
- Version columns for optimistic locking

### Validation Rules

#### Stock Request Validation
- Item name: 2-100 characters, not blank
- Quantity: Greater than 0.01, less than 999,999.99
- Decimal precision: Up to 2 decimal places

#### User Registration Validation
- First name: Required, not blank
- Last name: Required, not blank
- Email: Valid email format, required
- Password: Required, not blank
- User type: Must be ADMIN or USER

#### Business Rules
- Stock out operations cannot result in negative inventory
- Item names are case-sensitive for exact matching
- All monetary values use BigDecimal for precision
- Optimistic locking prevents concurrent modification conflicts

### Configuration

#### Default Settings
- Default page size: 10 items
- Default page number: 0 (first page)
- Default sort direction: Descending
- Default sort field: createdDateTime
- JWT token expiration: 24 hours
- Refresh token expiration: 7 days

#### CORS Configuration
- Allowed origins: localhost:3000, localhost:8081, 127.0.0.1:5500
- Allowed methods: GET, POST, PUT, DELETE, OPTIONS, HEAD, PATCH
- Allowed headers: Origin, Content-Type, Accept, Authorization, etc.
- Credentials support: Enabled
- Preflight cache: 1 hour

### Documentation

#### Postman Collections
- **Complete API Collection** (`SIJD Inventory Management System - Complete API.postman_collection.json`)
  - All endpoints with examples
  - Environment variables for token management
  - Automatic token extraction from login responses
  - Pre-configured test scenarios
  - Global test scripts for response validation

- **Simplified Collection** (`SIJD inventry-v2.postman_collection`)
  - Basic endpoint organization
  - Essential operations only

#### Environment Variables
- `baseUrl`: http://localhost:8080/sijd
- `version`: v1
- `authToken`: Auto-populated from login
- `refreshToken`: Auto-populated from login
- `userRole`: Auto-populated from login
- `searchQuery`: Configurable search term
- `stockInId`: Configurable for update/delete operations
- `stockOutId`: Configurable for update/delete operations

### Security Features

#### Authentication Security
- JWT tokens with configurable expiration
- Secure password hashing with BCrypt
- Refresh token mechanism for seamless user experience
- Stateless session management

#### API Security
- Role-based endpoint protection
- CORS configuration for secure cross-origin requests
- Request validation to prevent malicious input
- Optimistic locking to prevent data corruption

#### Database Security
- Parameterized queries to prevent SQL injection
- Connection pooling for efficient resource usage
- User privilege separation (application user vs admin)

### Performance Features

#### Database Optimization
- Proper indexing strategy
- Connection pooling
- Optimized query design
- Lazy loading for related entities

#### API Optimization
- Pagination for large datasets
- Efficient sorting mechanisms
- Minimal data transfer with targeted DTOs
- Optimistic locking for concurrent operations

### Error Handling

#### Validation Errors
- Field-level validation with detailed messages
- Nested JSON structure for complex validation errors
- HTTP 422 (Unprocessable Entity) for business rule violations
- HTTP 400 (Bad Request) for malformed requests

#### Business Logic Errors
- Custom exception hierarchy
- Meaningful error messages
- Proper HTTP status codes
- Detailed logging for debugging

#### System Errors
- Global exception handler
- Graceful error responses
- Error logging and monitoring
- Database constraint violation handling

## [0.1.0] - 2024-01-01

### Added
- Initial project setup
- Basic Spring Boot configuration
- Database connection setup
- Project structure establishment

### Changed
- N/A (Initial release)

### Deprecated
- N/A (Initial release)

### Removed
- N/A (Initial release)

### Fixed
- N/A (Initial release)

### Security
- Basic security configuration
- JWT implementation foundation

---

## Version History Summary

| Version | Release Date | Key Features |
|---------|-------------|--------------|
| 1.0.0   | 2024-01-15  | Complete inventory management system with authentication, CRUD operations, and comprehensive API |
| 0.1.0   | 2024-01-01  | Initial project setup and basic configuration |

## Upgrade Guide

### From 0.1.0 to 1.0.0
This is a major release with complete functionality implementation. Since 0.1.0 was just initial setup:

1. **Database Migration**: Run the application to auto-create tables
2. **Configuration**: Update application.properties with production settings
3. **Testing**: Import Postman collections for API testing
4. **Documentation**: Review UserGuide.md and DevGuide.md

## Breaking Changes

### Version 1.0.0
- Initial stable API release
- No breaking changes from previous versions as this is the first functional release

## Migration Notes

### Database Migration
- The application uses `spring.jpa.hibernate.ddl-auto=update` for automatic schema management
- No manual migration scripts required for version 1.0.0
- For production deployments, consider using `validate` mode with explicit migration scripts

### Configuration Changes
- JWT secret should be configured via environment variables in production
- Database credentials should be externalized
- CORS origins should be updated for production domains

## Support and Maintenance

### Long-term Support
- Version 1.0.0 will receive security updates and bug fixes
- Feature updates will be released as minor versions (1.1.0, 1.2.0, etc.)
- Breaking changes will only be introduced in major versions (2.0.0, 3.0.0, etc.)

### End of Life
- No end-of-life planned for version 1.0.0
- Minimum support period: 2 years from release date
- Security patches will be provided as needed

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details on:
- Code standards
- Testing requirements
- Pull request process
- Issue reporting

## License

This project is licensed under the [MIT License](LICENSE) - see the LICENSE file for details.

## Acknowledgments

- Spring Boot community for excellent documentation and examples
- JWT.io for JWT implementation guidance
- MySQL team for reliable database support
- All contributors and testers who helped make this release possible

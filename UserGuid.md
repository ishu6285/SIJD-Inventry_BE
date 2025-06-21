# SIJD Inventory Management System - User Guide

## Table of Contents
- [Overview](#overview)
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [Stock Management](#stock-management)
- [Data Retrieval](#data-retrieval)
- [API Endpoints](#api-endpoints)
- [Postman Collection](#postman-collection)
- [Troubleshooting](#troubleshooting)

## Overview

The SIJD Inventory Management System is a REST API-based application built with Spring Boot that provides comprehensive inventory tracking capabilities. The system supports user authentication, stock in/out operations, and real-time inventory monitoring.

### Key Features
- **User Authentication**: JWT-based authentication with role-based access control
- **Stock Management**: Add, remove, update, and delete stock operations
- **Real-time Tracking**: Current stock levels with audit trails
- **Search Functionality**: Item search with partial name matching
- **Pagination**: Efficient data retrieval with sorting options
- **Audit Trail**: Complete tracking of all stock movements

### User Roles
- **ADMIN**: Full access to all inventory operations
- **USER**: Limited access (if implemented in future versions)

## Getting Started

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Postman (for API testing)

### Base URL
```
http://localhost:8080/sijd/api/v1
```

### Environment Setup
The application connects to a MySQL database with the following default configuration:
- **Host**: 204.12.227.200:3306
- **Database**: sijd_db
- **Username**: sijd
- **Password**: sijd@123

## Authentication

### User Registration

Register a new user account:

**Endpoint**: `POST /auth/user/register`

**Request Body**:
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

**Response**:
```json
{
  "message": "Success",
  "data": {
    "user": {
      "userId": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "userType": "ADMIN"
    },
    "message": "user registration success"
  }
}
```

### User Login

Authenticate and receive JWT tokens:

**Endpoint**: `POST /auth/user/login`

**Request Body**:
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

**Response**:
```json
{
  "message": "Success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
    "expirationTime": "24h",
    "role": "ADMIN",
    "statusCode": 200,
    "message": "login success"
  }
}
```

### Token Refresh

Refresh your access token:

**Endpoint**: `POST /auth/user/refresh/token`

**Request Body**:
```json
{
  "token": "your_refresh_token_here"
}
```

### Authentication Headers

For all protected endpoints, include the JWT token in the Authorization header:
```
Authorization: Bearer your_jwt_token_here
```

## Stock Management

All stock management operations require ADMIN role authentication.

### Stock In (Add Inventory)

Add new items to inventory or increase existing stock:

**Endpoint**: `POST /admin/stock/in`

**Request Body**:
```json
{
  "itemName": "Dell Laptop",
  "quantity": 25.50
}
```

**Response Examples**:

*New Item*:
```json
"New item 'Dell Laptop' added to stock with quantity: 25.5"
```

*Existing Item*:
```json
"Stock in success for item 'Dell Laptop'. Added quantity: 10.0. New total: 35.5"
```

### Stock Out (Remove Inventory)

Remove items from inventory:

**Endpoint**: `POST /admin/stock/out`

**Request Body**:
```json
{
  "itemName": "Dell Laptop",
  "quantity": 5.00
}
```

**Response Examples**:

*Success*:
```json
"Stock out success for item 'Dell Laptop'. Quantity removed: 5.0. Remaining stock: 30.5"
```

*Insufficient Stock*:
```json
"Insufficient stock for item 'Dell Laptop'. Available: 2.5, Requested: 5.0"
```

*Item Not Found*:
```json
"Item 'NonExistent Item' not found in current stock"
```

### Update Stock In Records

Modify existing stock-in transactions:

**Endpoint**: `PUT /admin/stock/in/edit`

**Request Body**:
```json
{
  "stockInId": 1,
  "itemName": "Dell Laptop",
  "quantity": 30.00
}
```

### Update Stock Out Records

Modify existing stock-out transactions:

**Endpoint**: `PUT /admin/stock/out/edit`

**Request Body**:
```json
{
  "stockOutId": 1,
  "itemName": "Dell Laptop",
  "quantity": 8.00
}
```

### Delete Stock Records

Delete stock-in records:
**Endpoint**: `DELETE /admin/stock/in/delete/{stockInId}`

Delete stock-out records:
**Endpoint**: `DELETE /admin/stock/out/delete/{stockOutId}`

## Data Retrieval

### Get Current Stock

Retrieve paginated current inventory:

**Endpoint**: `GET /admin/get/all/current-stock`

**Query Parameters**:
- `page`: Page number (default: 0)
- `size`: Items per page (default: 10)
- `sortField`: Field to sort by (default: createdDateTime)
- `sortDirection`: Sort direction - asc/desc (default: desc)

**Example**:
```
GET /admin/get/all/current-stock?page=0&size=5&sortField=itemName&sortDirection=asc
```

**Response**:
```json
{
  "content": [
    {
      "itemId": 1,
      "itemName": "Dell Laptop",
      "itemQuantity": 30.50,
      "version": 2,
      "createdUser": "admin@gmail.com",
      "createdDateTime": "2024-01-15T10:30:00.000000",
      "modifiedUser": "admin@gmail.com",
      "modifiedDateTime": "2024-01-15T14:20:00.000000",
      "status": "ACTIVE"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### Get Stock In History

Retrieve paginated stock-in transaction history:

**Endpoint**: `GET /admin/get/all/stock-in`

**Query Parameters**: Same as current stock endpoint

### Get Stock Out History

Retrieve paginated stock-out transaction history:

**Endpoint**: `GET /admin/get/all/stock-out`

**Query Parameters**: Same as current stock endpoint

### Search Items

Search for items by partial name matching:

**Endpoint**: `GET /admin/item/search/{searchQuery}`

**Example**:
```
GET /admin/item/search/Lap
```

**Response**:
```json
[
  "Dell Laptop",
  "HP Laptop",
  "MacBook Laptop"
]
```

## API Endpoints Summary

### Authentication Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/user/register` | Register new user | No |
| POST | `/auth/user/login` | User login | No |
| POST | `/auth/user/refresh/token` | Refresh JWT token | No |

### Stock Management Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/admin/stock/in` | Add stock | ADMIN |
| POST | `/admin/stock/out` | Remove stock | ADMIN |
| PUT | `/admin/stock/in/edit` | Update stock-in record | ADMIN |
| PUT | `/admin/stock/out/edit` | Update stock-out record | ADMIN |
| DELETE | `/admin/stock/in/delete/{id}` | Delete stock-in record | ADMIN |
| DELETE | `/admin/stock/out/delete/{id}` | Delete stock-out record | ADMIN |

### Data Retrieval Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/admin/get/all/current-stock` | Get current inventory | ADMIN |
| GET | `/admin/get/all/stock-in` | Get stock-in history | ADMIN |
| GET | `/admin/get/all/stock-out` | Get stock-out history | ADMIN |
| GET | `/admin/item/search/{query}` | Search items | ADMIN |

## Postman Collection

The repository includes comprehensive Postman collections:

1. **SIJD Inventory Management System - Complete API.postman_collection.json**
   - Complete API collection with authentication
   - Environment variables for token management
   - Pre-configured test scenarios
   - Automatic token extraction from login responses

2. **SIJD inventry-v2.postman_collection**
   - Alternative collection format
   - Simplified endpoint organization

### Using the Postman Collection

1. Import the collection into Postman
2. Set up environment variables:
   - `baseUrl`: http://localhost:8080/sijd
   - `version`: v1
3. Run the "User Login" request first to authenticate
4. The collection will automatically store your JWT tokens
5. Proceed with stock management operations

### Environment Variables

The collection uses these environment variables:
- `baseUrl`: Base URL of the API
- `version`: API version
- `authToken`: JWT access token (auto-populated)
- `refreshToken`: Refresh token (auto-populated)
- `userRole`: User role (auto-populated)
- `searchQuery`: Search term for item search
- `stockInId`: ID for stock-in operations
- `stockOutId`: ID for stock-out operations

## Troubleshooting

### Common Issues

**1. Authentication Errors**
- Ensure JWT token is included in Authorization header
- Check token expiration (24 hours)
- Use refresh token endpoint to get new access token

**2. Database Connection Issues**
- Verify MySQL server is running
- Check database credentials in application.properties
- Ensure database `sijd_db` exists

**3. Validation Errors**
- Item name: 2-100 characters required
- Quantity: Must be greater than 0 and less than 999999.99
- Email: Must be valid email format

**4. Stock Operations**
- Stock out: Ensure sufficient quantity available
- Item not found: Check exact item name spelling
- Negative stock: System prevents negative inventory

### Error Response Format

```json
{
  "itemName": {
    "value": "invalid_value",
    "description": "Item name must be between 2 and 100 characters"
  }
}
```

### HTTP Status Codes

- `200`: Success
- `400`: Bad Request (validation errors)
- `401`: Unauthorized (authentication required)
- `403`: Forbidden (insufficient permissions)
- `422`: Unprocessable Entity (business logic errors)
- `500`: Internal Server Error

## Best Practices

1. **Always authenticate before making requests** to protected endpoints
2. **Use pagination** for large datasets to improve performance
3. **Handle token expiration** by implementing refresh token logic
4. **Validate input data** before sending requests
5. **Check response status codes** and error messages
6. **Use appropriate quantity precision** (up to 2 decimal places)
7. **Implement proper error handling** in your client applications

## Support

For technical support or questions about the API:
1. Check this documentation first
2. Review the Postman collection examples
3. Check the application logs for detailed error information
4. Ensure your database connection is properly configured

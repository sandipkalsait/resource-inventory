#Inventory Service

##The Inventory Service is a Spring Boot application that provides RESTful APIs for managing inventory. It uses Spring Security for authentication, Spring Boot Data JPA for CRUD operations, and PostgreSQL as the database.

##Features
- Authentication: Secured with Spring Security, using default username and password for access.
- CRUD Operations: Supports Create, Read, Update, and Delete operations for inventory management.
- Database: Utilizes PostgreSQL for persistent storage of inventory data.
- Spring Boot Data JPA: Integrated for database interactions and object-relational mapping (ORM).

##Prerequisites
Before running the application, ensure the following:

1. Java 17+ is installed.
2. Maven is installed for building the project.
3. PostgreSQL is installed and running. You should have a PostgreSQL database available for connection.

##Configuration

application.properties Configuration

Make sure to configure the application.properties file with your database connection details and Spring Security credentials.

spring.datasource.url=jdbc:postgresql://localhost:5432/inventorydb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.security.user.name=user
spring.security.user.password=password

logging.level.org.springframework.security=DEBUG

- Replace localhost:5432/inventorydb with your PostgreSQL instance details.
- Replace your_password with the PostgreSQL database password.

Database Schema
You will need to create a table for storing inventory items. Example schema:

CREATE TABLE inventory (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

Endpoints

Authentication
All endpoints are secured by Spring Security. Use the default credentials:
- Username: user
- Password: password

CRUD Operations

1. Create Inventory Item

Endpoint: POST /api/inventory

Request Body:
{
  "name": "Item Name",
  "description": "Item Description",
  "quantity": 100,
  "price": 9.99
}

2. Get All Inventory Items

Endpoint: GET /api/inventory

Response:
[
  {
    "id": 1,
    "name": "Item Name",
    "description": "Item Description",
    "quantity": 100,
    "price": 9.99
  },
  ...
]

3. Get Inventory Item by ID

Endpoint: GET /api/inventory/{id}

Response:
{
  "id": 1,
  "name": "Item Name",
  "description": "Item Description",
  "quantity": 100,
  "price": 9.99
}

4. Update Inventory Item

Endpoint: PUT /api/inventory/{id}

Request Body:
{
  "name": "Updated Item Name",
  "description": "Updated Item Description",
  "quantity": 150,
  "price": 19.99
}

5. Delete Inventory Item

Endpoint: DELETE /api/inventory/{id}

Running the Application
To run the application, follow these steps:

1. Clone the repository:
   git clone https://github.com/sandipkalsait/inventory-service.git
   cd inventory-service

2. Build the project using Maven:
   mvn clean install

3. Run the application:
   mvn spring-boot:run

The application will be available at http://localhost:8080.

Testing the API
You can test the API using tools like Postman or curl.

Example curl request to get all inventory items:

curl -u user:password http://localhost:8080/api/inventory

License
This project is licensed under the MIT License.""



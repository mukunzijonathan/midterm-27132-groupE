# Car Dealership Management System
**Mid-Term Practical Assessment — Web Technology**  
**Student:** Mukunzi Jonathan | **ID:** 27132  
**Branch:** `mid-term-exam-carDealership`
 
---
 
## Project Overview
 
A Spring Boot REST API managing a car dealership system with 5 entities and a full Rwanda administrative location hierarchy tree. Built with Java 17, Spring Boot, Spring Data JPA, and PostgreSQL.
 
---
 
## Tech Stack
 
| Layer | Technology |
|-------|-----------|
| Framework | Spring Boot 4.x |
| Language | Java 21 |
| Database | PostgreSQL 18 |
| ORM | Spring Data JPA / Hibernate |
| Build | Maven |
 
---
 
## Entity Relationship Summary
 
```
Location (UUID)  ──self-ref──►  Location (Province > District > Sector > Cell > Village)
Customer (UUID)  ──ManyToOne──► Location (Village level)
Employee (Long)  ──ManyToOne──► Location
Sale (Long)      ──ManyToOne──► Customer
Sale (Long)      ──ManyToOne──► Employee
Sale (Long)      ──ManyToMany─► Car  (via sale_car join table)
Car (Long)       ──ManyToMany─► Sale (mapped by "cars")
```
 
---
 
## Project Structure
 
```
src/main/java/com/example/CarDealership/
├── model/
│   ├── Location.java         # UUID PK, self-referencing tree
│   ├── Customer.java         # UUID PK, ManyToOne → Location
│   ├── Car.java              # Long PK, ManyToMany ↔ Sale
│   ├── Employee.java         # Long PK, ManyToOne → Location
│   └── Sale.java             # Long PK, ManyToMany → Car, ManyToOne → Customer + Employee
├── repository/
│   ├── LocationRepository.java
│   ├── CustomerRepository.java   # @Query province traversal methods
│   ├── CarRepository.java        # Page<Car> + Sort methods
│   ├── EmployeeRepository.java   # @Query province traversal methods
│   └── SaleRepository.java
├── service/
│   ├── LocationService.java
│   ├── CustomerService.java      # existsByEmail duplicate check
│   ├── CarService.java           # pagination + sorting logic
│   ├── EmployeeService.java
│   └── SaleService.java
└── controller/
    ├── LocationController.java
    ├── CustomerController.java
    ├── CarController.java
    ├── EmployeeController.java
    └── SaleController.java
```
 
---
 
## Setup & Running
 
### Prerequisites
- Java 17+
- PostgreSQL running on port 5432
- Maven
 
### Database Setup
```sql
CREATE DATABASE "car-dealership";
```
 
### application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/car-dealership
spring.datasource.username=postgres
spring.datasource.password=(I'll add my password)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```
 
### Run the application
```bash
mvn spring-boot:run
```
App starts on `http://localhost:8080`
 
---
 
## API Endpoints
 
### Location
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/locations/add` | Save a location (Province to Village) |
| GET | `/api/locations/all` | Get all locations |
| GET | `/api/locations/provinces` | Get all provinces |
| GET | `/api/locations/children?parentId=` | Get children of a location |
| GET | `/api/locations/getById?id=` | Get location by UUID |
 
### Customer
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/customers/add` | Save customer (Village UUID required) |
| GET | `/api/customers/all` | Get all customers |
| GET | `/api/customers/getById?id=` | Get customer by UUID |
| GET | `/api/customers/province?name=Kigali` | Get customers by province name |
| GET | `/api/customers/provinceId?id=` | Get customers by province UUID |
| PUT | `/api/customers/update?id=` | Update customer |
| DELETE | `/api/customers/delete?id=` | Delete customer |
 
### Car
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/cars/add` | Save car (existsBy brand+model+year) |
| GET | `/api/cars/all` | Get all cars |
| GET | `/api/cars/paginated?page=0&size=5` | Paginated cars |
| GET | `/api/cars/sorted/price` | Cars sorted by price (ASC) |
| GET | `/api/cars/sorted/year` | Cars sorted by year (DESC) |
 
### Employee
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/employees/add` | Save employee |
| GET | `/api/employees/all` | Get all employees |
| GET | `/api/employees/province?name=` | Employees by province name |
| GET | `/api/employees/provinceId?id=` | Employees by province UUID |
 
### Sale
| Method | URL | Description |
|--------|-----|-------------|
| POST | `/api/sales/add` | Save sale with list of cars (Many-to-Many) |
| GET | `/api/sales/all` | Get all sales |
| GET | `/api/sales/getById?id=` | Get sale by ID (shows linked cars) |
 
---
 
## Key Implementation Highlights
 
### 1. Self-Referencing Location Tree
```java
@ManyToOne
@JoinColumn(name = "parent_id")
private Location parent;  // Province has parent = null
```
 
### 2. Province Query (traverses 4 levels)
```java
@Query("SELECT c FROM Customer c WHERE " +
       "c.location.parent.parent.parent.parent.name = :name OR ...")
List<Customer> findByProvinceName(@Param("name") String name);
```
 
### 3. Pagination
```java
Pageable pageable = PageRequest.of(page, size);
return carRepository.findAll(pageable);
```
 
### 4. Sorting
```java
return carRepository.findAll(Sort.by(Sort.Direction.ASC, "price"));
```
 
### 5. existsBy Duplicate Prevention
```java
if (customerRepository.existsByEmail(customer.getEmail())) {
    return "Customer with this email already exists";
}
```
 
### 6. Many-to-Many Join Table
```java
@ManyToMany
@JoinTable(name = "sale_car",
    joinColumns = @JoinColumn(name = "sale_id"),
    inverseJoinColumns = @JoinColumn(name = "car_id"))
private List<Car> cars;
```
 
---

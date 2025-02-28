# Spring Boot Shopping Cart Backend

This is a Spring Boot 3.4.2 backend application using Java 21. The application implements JWT authentication with HttpOnly cookies, role-based authentication, custom CORS configuration, and functionalities like cart management, orders, and image uploads.

## Features

- **Spring Security with JWT Authentication** (HttpOnly cookies for secure authentication)
- **Role-Based Authentication** (Admin, User)
- **Public URLs Without Authentication**
- **Custom CORS Configuration**
- **Cart, Orders, and Image Uploading Functionalities**
- **MySQL Database Integration**
- **Spring Data JPA for Database Interaction**
- **Lombok for Reducing Boilerplate Code**
- **Postman for API Testing**
- **Follows Best Practices**

## Prerequisites

Ensure you have the following installed before proceeding:

- Java 21
- Maven 3+
- MySQL Server

## Cloning and Running the Application

### Step 1: Clone the Repository
```bash
 git clone https://github.com/e19465/spring-boot-shopping-cart-BE.git
 cd spring-boot-shopping-cart-BE
```

### Step 2: Configure Database
Update `application.properties` (or `application.yml`) with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_cart_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Step 3: Build and Run the Application

#### Using Maven
```bash
mvn clean install
mvn spring-boot:run
```

#### Using Java
```bash
mvn clean package
java -jar target/*.jar
```

### Step 4: Testing with Postman
Use Postman or any API client to test the endpoints. Ensure that you have authentication headers set for secured endpoints.

## Technologies Used

- **Spring Boot 3.4.2**
- **Spring Security**
- **JWT Authentication**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **Maven**
- **Postman for API Testing**

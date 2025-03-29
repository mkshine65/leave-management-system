# Leave Management System

A Spring Boot application for managing employee leave requests.

## Features

- Employee leave request submission
- Leave balance tracking
- Manager approval/rejection workflow
- Role-based access control
- Responsive UI with Bootstrap

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- Git

## Local Development Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd leave-management-system
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

4. Access the application:
- Main application: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: jdbc:h2:file:./data/leavemanagement
  - Username: sa
  - Password: password

## Default Users

1. Manager:
   - Email: admin@example.com
   - Password: admin123

2. Employee:
   - Email: john@example.com
   - Password: admin123

## Deployment to Heroku

1. Create a Heroku account and install Heroku CLI

2. Login to Heroku:
```bash
heroku login
```

3. Create a new Heroku app:
```bash
heroku create leave-management-system
```

4. Add PostgreSQL addon:
```bash
heroku addons:create heroku-postgresql:hobby-dev
```

5. Deploy the application:
```bash
git push heroku main
```

6. Open the application:
```bash
heroku open
```

## Deployment to Railway.app

1. Create a Railway.app account
2. Connect your GitHub repository
3. Create a new project
4. Add PostgreSQL database
5. Deploy the application

## Deployment to Render

1. Create a Render account
2. Connect your GitHub repository
3. Create a new Web Service
4. Add PostgreSQL database
5. Deploy the application

## Technologies Used

- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- Thymeleaf
- H2 Database (local) / PostgreSQL (production)
- Bootstrap 5
- Lombok 
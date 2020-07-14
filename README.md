# Software Design Document 

##  Description of the Product

Registration and login infrastructure for the web applications.

##  The scope of the work

Providing Registration user interface, login functionality for regular users and admin user, reporting module for admin users

## Milestones

M1. Registration user interface
M2. Sending Confirmation mail to the new user
M3. Login user interface
M4. Logout
M5. Reporting user interface for admin user

## Assumptions

1. There is only one admin user on the web application and it is created by database script
2. New users have USER role
3. Login can be done only username and password fields. Email, name, surname are additional info. These fields can not be used for login.

## Workflows

### Registration WF

Registration Form --> Email Confirmation --> User Activation

### Login WF

Login Form --> Authentication from DB --> User Registration Complete Check --> DB Login Status Update --> Home Page 

### Logout WF

Logout Link --> DB Login Status Update --> Home Page 

## Technology Stack

#### Java 8, Spring Boot Release 2.2.4 (Data JPA, Web, Security, Thymeleaf, Mail), MySQL

## Database Design

#### Users Table
All users are stored here. Has Many-To-Many relationship with Role table

#### Roles Table
Predefined USER and ADMIN roles are stored here.

#### Token Table
Auto generated tokens are stored here and deleted after successful registration. Has One-To-One relationship with User Table


## Test Scenarios

### Registration Scenarios

1. Happy path with all fields filled properly and complete confirmation step
2. Wait 24 hours to expiration of confirmation mail and check error page

### Login Scenarios

1. Happy Path with activated user and navigate to home page
2. Try to login not confirmed user and check error message
3. Try to login with non-existing user and check error message

### Reporting Scenarios

1. Login with admin user and navigate to report home page
2. Click online user report and check the number against DB table(User table loggedin column)
3. Click expired confirmation report and check the number against DB table(Token table creation_time column)
3. Click new user report and check the number against DB table(User table registration_complete_time column)

## Missing Requirements

1. Remind password
2. Average Login time report

## Improvement Points

1. Unit testing
2. DB profiles in Spring Data JPA (H2 for development, MySQL for test&prod)
3. Logging
4. I18n
5. Privilege table for to store url for actions

### Prerequisite

1. MySQL should be up and running
2. login db should be created before starting app (CREATE DATABASE IF NOT EXISTS login;)
3. SMTP server configuration should be ready in application.properties file

Note: No need to create tables and insert data. 
data.sql script inserts data and hibernate configuration creates tables during app startup.


1. mvn clean install
2. java -jar target/login.jar

- http://localhost:8080/registration
- http://localhost:8080/login

### Docker
- mvn clean install
- docker build --tag login .
- docker run -it -p 8080:8080 --name login
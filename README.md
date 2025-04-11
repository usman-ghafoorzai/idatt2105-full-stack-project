### Frontend - Vue

### Build & Run

#### Tech Stack
 - HTML
 - CSS
 - Javascript
 - Vue.js 3.5.13 (Latest as of 11.04)
 - Vitest 3.0.8

**Prerequisites:**
 - node js from https://nodejs.org/en
 
**Build and run:**
 - Download the file and navigate to the frontend folder
 - Run the command "npm install" to install dependencies
 - Then run the command "npm run dev"
 - Open localhost:5713

**Testing:**
 - Follow same steps as in build and run, but instead of writing "npm run dev",
   write the command "npx vitest"


### Backend - Spring Boot

#### Tech Stack
- Java 21
- Spring Boot 3.4.4
- Maven
- Spring Web & Spring Data JPA
- Spring Security with JWT (JJWT)
- MySQL (runtime) and H2 (for development/testing)
- SpringDoc OpenAPI for API documentation
- JUnit 5 and Jacoco for testing and code coverage

---

#### Build & Run

**Prerequisites:**
- Java 21 or higher
- Maven 3.8+

**Build the Project:**
mvn clean install
mvn spring-boot:run

**Configuration:**
Set which properties file to use here src/main/resources/application.properties by changing spring.profiles.active=x
standard is using a H2 database in the profile "submission"
If you want to use namox or other Mysql database by changing profile to prod and changing settings in application-prod.properties

**Testing:**
Run unit and integration tests:
- mvn test

Generate code coverage report:
- mvn verify

Coverage report will be avaliable at:
- target/site/jacoco/index.html

**Login:**
Login to already existing user:
 - username: test
 - password: password

**Security:**
The application uses JWT for authentication and authorization, implemented using the jjwt library. Secure endpoints require a valid Bearer token in the Authorization header.

**API Access**
Base URL:
http://localhost:8888

SwaggerUI:
http://localhost:8080/swagger-ui.html

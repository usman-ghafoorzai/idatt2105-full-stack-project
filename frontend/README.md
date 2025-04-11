### Frontend - Vue

### Build & Run

**Prerequisites:**
To be able to run the project there are a few things that need to be done.
Step one

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


**Security:**
The application uses JWT for authentication and authorization, implemented using the jjwt library. Secure endpoints require a valid Bearer token in the Authorization header.

**API Access**
Base URL:
http://localhost:8888

SwaggerUI:
http://localhost:8080/swagger-ui.html

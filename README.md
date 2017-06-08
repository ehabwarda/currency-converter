# currency-converter
web based currency converter based on openexchangerates public api
https://openexchangerates.org

# Technology stack
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Security
- Spring Data JPA
- Spring Cache
- Spring Test (MockMVC)
- H2 (in memory database)

# Features
- login/registration/logout
- main screen to query historical or current exchange rates (as live is not supported for free accounts I use latest instead of live)
- supports caching for historical exchanges, no need to call external service every time for same request.
- list of supported countries are configurable

# Build and Run
Application is based on spring boot which means it is autonomous and self contained as it contains all required dependencies and configurations.

To build and run, after you pull the application, follow one of the following approaches after you browse to the project path where the pom file located using CMD:

1- build and run using spring boot as following: 
- $ mvn spring-boot:run

or

2- build and run in two steps as following:
- $ mvn package
- $ java -jar target/currency-coverter-0.0.1-SNAPSHOT.jar

then from your favorite browser, navigate to following url and test the application:
- http://localhost:9090/

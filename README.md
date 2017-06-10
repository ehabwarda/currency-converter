# Description
- web based currency converter based on openexchangerates public api: https://openexchangerates.org
- you can use this application to get latest currency conversion rates or at specific dates.

# Technologies
- Java 8
- H2 (in memory database)
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Security
- Spring Data JPA
- Spring Cache
- Spring Test (MockMVC and web integration)
- Spring Cloud Hystrix (circuit breaker implementation)

# Features
- login/registration/logout
- secured login using spring security (database authentication)
- Passwords are encoded in DB
- main screen to query historical or latest available exchange rates.
- supports caching for historical exchanges, no need to call external service every time for same request.
- cache TTL and TTI are configurable
- list of supported countries are configurable
- form validation messages are configurable
- list of supported currencies are retrieved from openexchangerates service and cached (configurable TTL). I used hystrix here to get the list from configuration (default list) if failed to connect to external service or if time out.
- all openexchangerates endpoints and app_id are configurable.
- monitoring and management interfaces (JMX, REST) are provided through actuator. for simplicity, I provided ACTUATOR role to all users so that they can access actuator's endpoints and metrics, for example: /metrics and /trace. Meanwhile, actuator can be extended to add custom metrics if needed.

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

# Notes
- it depends on the openexchangerates account to allow all operations or not. here, as we use a free account, only from currency supported is USD. the openexchangerates app_id is configurable and if updated to paid account, all operations will be available.
- it is one main page for both latest and historical queries, if date not provided it will be considered as a request for latest exchange.
- for testing, I created a user while startup >> email: test@gmail.com	password: password

# Restrictions
- always use USD as from currency because of limitations on openexchangerates free account. because of that, I made USD is the only available currency in from currency list.
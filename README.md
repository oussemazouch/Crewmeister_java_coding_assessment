# Crewmeister Test Assignment - Java Backend Developer

## The Challenge

I am tasked  to create a foreign exchange rate service as SpringBoot-based microservice. 

The exchange rates can be received from [2]. This is a public service provided by the German central bank.

As we are using user story format to specify our requirements, here are the user stories to implement:

- As a client, I want to get a list of all available currencies
- As a client, I want to get all EUR-FX exchange rates at all available dates as a collection
- As a client, I want to get the EUR-FX exchange rate at particular day
- As a client, I want to get a foreign exchange amount for a given currency converted to EUR on a particular day
 
## Setup
#### Requirements
- Java 11 (will run with OpenSDK 15 as well)
- Maven 3.x

#### Project
The project was generated through the Spring initializer [1] for Java
 11 with dev tools and Spring Web as dependencies. In order to build and 
 run it, you just need to click the green arrow in the Application class in your Intellij 
 CE IDE or run the following command from your project root und Linux or ios. 

````shell script
$ mvn spring-boot:run
````
[1] https://start.spring.io/

[2] [Bundesbank Daily Exchange Rates](https://www.bundesbank.de/dynamic/action/en/statistics/time-series-databases/time-series-databases/759784/759784?statisticType=BBK_ITS&listId=www_sdks_b01012_3&treeAnchor=WECHSELKURSE)
## Important Notes 🚨

### 1. **Database Initialization**
- Once the Spring Boot application is launched, database initialization is required to fetch all data from the bank's API.
- Initialization is performed by sending a `GET` request to the `/api/init` endpoint.
- You can optionally specify a starting date as a query parameter (e.g., `/api/init?startDate=YYYY-MM-DD`). If no date is provided, the system will use a default starting date.
- This process may take several seconds to minutes, depending on the chosen starting date and the volume of data.

### 2. **Post-Initialization**
- Ensure the initialization process is complete before interacting with other endpoints.
- After initialization, users can perform various tasks as outlined in the user stories.

### 3. **API Documentation**
- For detailed information on how to use the API, refer to [My Postman Collection](https://drive.google.com/file/d/1jg617wGPWCeks64z2gt5alUNneePKXLr/view).
- The collection provides comprehensive details about the endpoints, their usage, and example requests.
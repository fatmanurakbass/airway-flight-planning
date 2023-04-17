# Airway Flight Planning Project

This project is a web application for managing and querying airline flight planning. RESTful APIs are developed using Spring Boot, JPA, and Hibernate in the project.

## Technologies and Libraries Used

* Spring Boot: A Java-based framework used for building the foundation of the application.
* JPA and Hibernate: Java libraries used for database operations and ORM (Object Relational Mapping).
* ModelMapper: A Java library for providing automatic transformation between objects.
* Mockito: A Java library for simulating the behavior of objects in unit tests.
* JUnit: A Java library for writing and running unit tests.
* Jackson: A Java library for converting JSON data to Java objects and Java objects to JSON data.

## Installation and Running

To run the project, follow the steps below:

1. Clone the project to your local machine:

git clone https://github.com/fatmanurakbass/airway-flight-planning.git


2. Open the project with an IDE like IntelliJ IDEA or Eclipse.

3. Run the application (e.g., by running the Spring Boot application).

4. Use an HTTP client like Postman or curl to test the APIs.

## Sample Requests

Here are sample requests for each method in the `FlightController` class:

### Create a Flight

POST /flights
Content-Type: application/json

{
"airlineCode": "FN",
"sourceAirportCode": "IST",
"destinationAirportCode": "JFK",
"departureTime": "2023-04-20T10:30:00",
"arrivalTime": "2023-04-20T16:00:00",
"duration": "05:30:00"
}

### Get All Flights

GET /flight

### Get Flight by ID

GET /flights/{id}

### Update Flight

PUT /flight/{id}
Content-Type: application/json

{
"airlineCode": "TK",
"sourceAirportCode": "IST",
"destinationAirportCode": "JFK",
"departureTime": "2023-04-20T13:00:00",
"arrivalTime": "2023-04-20T19:00:00",
"duration": "05:30:00"
}

### Delete Flight

DELETE /flights/{id}
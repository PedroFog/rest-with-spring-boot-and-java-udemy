# Challenge Loans

This project is part of a course on Udemy about using Spring with AWS.

## Tech Stack

- Java 17
- Spring Boot 3
- JUnit
- TestContainers
- Swagger
- Rest Assured
- MySQL
- Flyway
- Security

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- JDK 17
- Maven
- Docker and Docker Compose
- Git

## Running the Project

1. **Clone the repository:**

    ```bash
    git clone https://github.com/PedroFog/rest-with-spring-boot-and-java-udemy.git
    ```

2. **Import the project into your IDE.**

3. **Run the project:**

    You can run the project directly from your IDE or via the command line:

    ```bash
    mvn spring-boot:run
    ```

## Running with Docker Compose

1. **Build the application:**

    ```bash
    mvn clean package -DskipTests
    ```

2. **Navigate to the directory containing the Docker Compose file and run:**

    ```bash
    docker-compose up -d --build
    ```

    This will build and start the application along with any required services (e.g., MySQL).

## Try it Out

Once the application is running, you can try it out using Swagger UI or the provided Postman collection.

### Using Swagger UI

1. **Access the Swagger UI:**

    Open your web browser and navigate to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html). The Swagger UI provides a user-friendly interface to interact with the API endpoints.

### Using Postman

1. **Import the Postman Collection:**

    The Postman collection file is located in the `postman` directory of the project. Import this collection into Postman by following these steps:

    - Open Postman.
    - Click on `Import` in the top-left corner.
    - Select `Upload Files`.
    - Choose the file `Curso Udemy Restful API to Aws Copy.postman_collection.json` from the starter directory.
    - Click `Import`.

2. **Use the Collection:**

    Once the collection is imported, you can use the predefined requests to interact with the API. The collection includes examples for all available endpoints.

3. **Make API Requests:**

    You can run the requests directly from Postman. Ensure the environment variables (e.g., `baseUrl`) are correctly set to `http://localhost:8080`.

## Run Tests

1. **Ensure Docker Desktop is not logged in:**

    Before running the tests, make sure that Docker Desktop is running but not logged into Docker Hub. This is necessary for the TestContainers to work correctly.

2. **Configure TestContainers properties (if Docker Desktop is logged in):**

    If you are logged into Docker Hub, you need to configure the `testcontainers.properties` file to avoid authentication issues.

3. **Run the tests using Maven:**

    ```bash
    mvn test
    ```

## Developed by

Pedro Luiz Foganholi

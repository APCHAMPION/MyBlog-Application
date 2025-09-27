---

## Prerequisites

Before you begin, ensure you have the following software installed on your machine:

- **Java JDK 17 or later:** The application is built on Java 17.
- **Apache Maven 3.6 or later:** Used for dependency management and building the project. The included Maven Wrapper (`mvnw`) can handle this for you.
- **Docker:** Required for building the application image and running it in a containerized, production-like environment.
- **A running PostgreSQL instance:** Required only for running the application in production mode with Docker.

---

## How to Run

There are two primary ways to run this application, designed for different purposes.

### 1. Running Locally (Development Mode)

This method is ideal for quick development, testing, and seeing your code changes live. It uses the embedded H2 in-memory database, which means all data (posts, users, comments) will be reset every time the application restarts.

1.  **Clone the repository:**
    First, clone the project from GitHub to your local machine.
    ```bash
    git clone https://github.com/your-username/simple-blog-platform.git
    cd simple-blog-platform
    ```

2.  **Run the application using the Maven Wrapper:**
    The project includes a Maven Wrapper (`mvnw`), which is the recommended way to build and run the application. It automatically downloads the correct version of Maven, ensuring a consistent environment.

    ```bash
    # On macOS or Linux
    ./mvnw spring-boot:run

    # On Windows
    mvnw.cmd spring-boot:run
    ```

3.  **Access the application:**
    Once the application starts, it will be available at `http://localhost:8080`.

### 2. Running with Docker (Production Mode)

This method simulates a real-world production deployment. The application is packaged into a Docker container and configured to connect to a persistent PostgreSQL database.

1.  **Set up the PostgreSQL Database:**
    - Ensure you have a PostgreSQL server running and accessible.
    - Create a dedicated database for the application (e.g., `blog_prod_db`).
    - Create a dedicated user with a password that has all privileges on that database.

2.  **Configure Production Properties:**
    - In the project, navigate to `src/main/resources/application-prod.properties`.
    - Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties to match your PostgreSQL setup.

3.  **Package the Application:**
    Before building the Docker image, you need to package the application into an executable `.jar` file using Maven.
    ```bash
    # On macOS or Linux
    ./mvnw clean package

    # On Windows
    mvnw.cmd clean package
    ```
    This command cleans any previous builds and creates a new `.jar` file in the `/target` directory.

4.  **Build the Docker Image:**
    Use the provided `Dockerfile` to build a Docker image for the application. The `-t` flag tags the image with a memorable name.
    ```bash
    docker build -t simple-blog-platform .
    ```

5.  **Run the Docker Container:**
    Run the image you just built as a container. This command connects the container to the network and activates the production profile.
    ```bash
    docker run -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=prod" --name blog-app simple-blog-platform
    ```
    Let's break down this command:
    - `-p 8080:8080`: Maps port 8080 on your host machine to port 8080 inside the container, allowing you to access the application.
    - `-e "SPRING_PROFILES_ACTIVE=prod"`: Sets an environment variable inside the container. Spring Boot automatically detects this and activates the `prod` profile, causing it to use the `application-prod.properties` file and connect to PostgreSQL.
    - `--name blog-app`: Gives your running container a convenient name.

6.  **Access the application:**
    The application is now running in a container and is accessible at `http://localhost:8080`. Any data you create will now be saved in your PostgreSQL database and will persist even if you stop and restart the container.

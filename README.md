# Configurable Tic-Tac-Toe Web Game

This is a configurable web-based Tic-Tac-Toe game implemented in Java using the Spring Boot framework.

## Features

- Configurable board size (3x3, 5x5, 9x9, etc.)
- Web-based interface
- RESTful API for game operations
- Object-Oriented Design
- Clean code with separation of concerns

## Technologies Used

- Java 11
- Spring Boot
- Maven for dependency management
- HTML, CSS, JavaScript for the frontend

## How to Run

1. Clone the repository
2. Build the project using Maven:
   ```
   mvn clean install
   ```
3. Run the application:
   ```
   mvn spring-boot:run
   ```
4. Open your browser and navigate to `http://localhost:8080/`

## Project Structure

- `model`: Contains the game's domain classes
- `service`: Contains business logic
- `controller`: Handles HTTP requests
- `resources`: Contains frontend files (HTML, CSS, JS)

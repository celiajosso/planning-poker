# Planning Poker

This project is a **Planning Poker** application developed with a **Ktor** backend architecture and a **Svelte** frontend. It includes modern features such as JWT authentication, OpenAPI documentation, and Docker integration for deployment.

## Project Structure

```
Project-pocker/
├── planning-poker/
│   ├── backend/       # Backend based on Ktor
│   ├── frontend/      # Frontend based on Svelte
│   ├── docs/          # Documentation and examples
│   ├── .idea/         # IDE configuration files
│   ├── Dockerfile     # Docker configuration
│   └── README.md      # Project documentation
```

## Features

### Backend
The backend is built with **Ktor** and includes the following features:
- **Routing**: Route management with a structured DSL.
- **JWT Authentication**: Secure authentication using JSON Web Tokens.
- **OpenAPI and Swagger**: Interactive API documentation.
- **Content Negotiation**: Automatic content conversion based on `Content-Type` and `Accept` headers.
- **kotlinx.serialization**: JSON serialization with the Kotlinx library.

### Frontend
The frontend uses **Svelte** for a modern and reactive user experience.

### Docker
The project is fully containerized with Docker, enabling easy deployment on any platform.

## Prerequisites

- **Java 20** for the backend.
- **Node.js** for the frontend.
- **Docker** for deployment.

## Installation

### Backend
1. Navigate to the `backend` folder:
   ```bash
   cd planning-poker/backend
   ```
2. Build the project with Gradle:
   ```bash
   ./gradlew build
   ```

### Frontend
1. Navigate to the `frontend` folder:
   ```bash
   cd planning-poker/frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```

## Deployment with Docker

1. Build and start the containers:
   ```bash
   docker compose up --build
   ```
2. Access the application:
   - Frontend: [http://localhost:5173](http://localhost:5173)
   - Backend: [http://localhost:8080](http://localhost:8080)

## Documentation

API documentation is available via Swagger at the following address:  
[http://localhost:8080/swagger](http://localhost:8080/swagger)

## Contribution

1. Fork the project.
2. Create a branch for your feature (`git checkout -b feature/my-feature`).
3. Commit your changes (`git commit -m 'Add my feature'`).
4. Push your branch (`git push origin feature/my-feature`).
5. Open a Pull Request.

## License

This project is licensed under the [Apache 2.0](https://www.apache.org/licenses/LICENSE-2.0) license.
# Planning Poker

Made by :
- CHAUVEL Tom
- LECORGNE Elouan
- JOSSO Célia

## Needed Tools

- At least **Java 17** for the backend.
- **Node.js** for the frontend.
- **Docker** for deployment.

## Project Configuration

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

### Docker

1. Build and start the containers:
   ```bash
   docker compose up --build
   ```
2. Access the application:
   - Frontend: [http://localhost:5173](http://localhost:5173)
   - Backend: [http://localhost:8080](http://localhost:8080)

## Deploy the application to production

WIP

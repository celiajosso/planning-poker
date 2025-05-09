FROM node:23.11.0-alpine AS node

WORKDIR /app

COPY ./frontend/package*.json ./
RUN npm install

COPY ./frontend .
RUN npm run build

FROM gradle:alpine AS gradle
WORKDIR /app

COPY ./backend/build.gradle.kts ./backend/settings.gradle.kts ./backend/gradle.properties ./
COPY ./backend/gradle ./gradle
RUN gradle dependencies

COPY ./backend .
RUN gradle build

FROM openjdk:25-slim AS openjdk
WORKDIR /app

COPY --from=node /app/build "/app/dist"
COPY --from=gradle /app/build/libs/backend-all.jar "/app"

EXPOSE 8080

CMD ["java","-jar","/app/backend-all.jar"]
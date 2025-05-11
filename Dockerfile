FROM node:23.11.0-alpine AS node

WORKDIR /app

COPY ./frontend/package*.json ./
RUN npm install

COPY ./frontend .
RUN npm run buildprod

FROM gradle:alpine AS gradle
WORKDIR /app

RUN jlink \
    --module-path "$JAVA_HOME/jmods" \
    --add-modules java.base,java.xml,java.naming,java.net.http \
    --verbose \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /opt/jre-minimal

COPY ./backend/build.gradle.kts ./backend/settings.gradle.kts ./backend/gradle.properties ./
COPY ./backend/gradle ./gradle
RUN gradle dependencies

COPY ./backend .
RUN gradle build

FROM alpine:latest AS jre
WORKDIR /app

COPY --from=gradle /opt/jre-minimal /opt/jre-minimal

ENV JAVA_HOME=/opt/jre-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"

COPY --from=node /app/build "/app/dist"
COPY --from=gradle /app/build/libs/backend-all.jar "/app"

EXPOSE 8080

CMD ["java","-jar","/app/backend-all.jar"]
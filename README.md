# Planning Poker

Made by :
- CHAUVEL Tom
- LECORGNE Elouan
- JOSSO Célia

## Needed Tools

- **Docker**

## Build

First clone the project
```
git clone https://github.com/celiajosso/planning-poker.git
```

Then in the root folder, create a .env file like so :

```
//.env

MONGO_USERNAME=sammy
MONGO_PASSWORD=your_password
MONGO_PORT=27017
MONGO_DB=sharkinfo
```

### For Developpers : 

1. Build and start the containers:
   ```bash
   docker compose up --watch
   ```
2. Access the application:
   [http://localhost:8080](http://localhost:8080)

### For Users : 
1. Build and start the containers:
   ```bash
   docker compose up --build
   ```
2. Access the application:
   [http://localhost:8080](http://localhost:8080)

## Deploy

### With Docker Compose :

You will need to create two file
- `compose.yml`
- `.env`

```yml
// compose.yml
services:
  db:
    image: mongo
    restart: unless-stopped
    env_file:
      - .env
    environment:
      - MONGO_INITDB_ROOT_USERNAME=$MONGO_USERNAME
      - MONGO_INITDB_ROOT_PASSWORD=$MONGO_PASSWORD
    volumes:
      - dbdata:/data/db
  app:
    image : ghcr.io/celiajosso/planning-poker:main
    env_file:
      - .env
    environment:
      - DB_MONGO_USER=$MONGO_USERNAME
      - DB_MONGO_PASSWORD=$MONGO_PASSWORD
      - DB_MONGO_HOST=db
      - DB_MONGO_PORT=27017
      - DB_MONGO_MAX_POOL_SIZE=20
      - DB_MONGO_DB=$MONGO_DB
      - NODE_ENV=production
      - DB_MONGO_VERSION=
    depends_on:
      - db
    ports:
      - 8080:8080
volumes:
  dbdata:
```
```
//.env

MONGO_USERNAME=sammy
MONGO_PASSWORD=your_password
MONGO_PORT=27017
MONGO_DB=sharkinfo
```

# Planning Poker

Made by :
- CHAUVEL Tom
- JOSSO Célia
- LECORGNE Elouan

## User documentation

### Login & Register

You can login and register with the `/login` and `/register` endpoints.
If you are not logged in, your planning poker history won't be shown in the frontend part.

You have some verifications when logging in or registering:

- Your password is at least 8 characters long, with numbers, uppercases and lowercases
- Your password and password confirmation match (registration)
- You accepted the gdpr policy (registration)

Here are the `/login` and `/register` pages, and an example of an error message if some fields are wrong:
<p align="center">
  <img src="https://github.com/user-attachments/assets/420f4a08-2f4f-465c-8212-a4b2ff970431" height="250"/>
  <img src="https://github.com/user-attachments/assets/a1ef610d-8a64-4d83-9750-04f2532b5b1a" height="250"/>
  <img src="https://github.com/user-attachments/assets/71654a17-2038-4b72-be9b-735725d983fe" height="100"/>
</p>

Here is the home page, respectively when you logged in and when you are not logged in. You can see that if you are not connected, you do not access to the icon at the top right of the page (respectively the history of the planning poker estimations and the logout button) and you have a random username you can change if you want

<p align="center">
  <img src="https://github.com/user-attachments/assets/bf3736a1-4398-4a80-81e1-b90185cba23c" height="250"/>
  <img src="https://github.com/user-attachments/assets/60d5d524-e2d9-45a5-b49d-89da29501375" height="250"/>
</p>

## Create & Join a room 

You can create or join a room, even if you are not logged in.

You are the admin (and the only one) of the room if you created it.

To join a room, type the room id the admin provided to you.

<p align="center">
  <img src="https://github.com/user-attachments/assets/90e2583a-594b-4659-9569-60f11a03f02f" height="250"/>
</p>

To create a room, simply click on "Create".

<p align="center">
  <img src="https://github.com/user-attachments/assets/7e732d66-f05b-4262-ba34-56ff80bc23e8" height="250"/>
</p>

## Room

When you create the room, you have this display:

<p align="center">
  <img src="https://github.com/user-attachments/assets/e069ee9c-5ec8-4b55-8c05-bddf12af2ef1" height="250"/>
</p>

 ### Icons

 #### Side panel

 ![image](https://github.com/user-attachments/assets/67b6abda-f769-416a-93c9-f1368eb0164a)

This icon is to toggle the side panel. Here is what it looks like at the beginning for the issue tab (only for administrators) :

<p align="center">
  <img src="https://github.com/user-attachments/assets/1941d812-4e1c-4f31-9a90-3d4cf2c890da" height="250"/>
</p>

##### Import issues

![image](https://github.com/user-attachments/assets/96b08115-faaa-467c-bd53-eebfd1b72832)

This icon is to import an issue (Jira CSV format)

##### Export issues

![image](https://github.com/user-attachments/assets/313cd1f4-ee53-4282-b0f4-22e6c2ef85b3)

This icon is to export an issue (Jira CSV format)

#### Add an issue

![image](https://github.com/user-attachments/assets/d4d11e5f-4a54-4dad-b510-2e27650d203a)

This button is to add a story.

Then, when you click on it, you have to enter a title and a description. Both are required.

<p align="center">
  <img src="https://github.com/user-attachments/assets/7336c671-6880-4232-a33d-5a58d2cec358" height="250"/>
</p>

Once added, the table looks like that

<p align="center">
  <img src="https://github.com/user-attachments/assets/9828c744-60bb-4e99-9bca-3415a2ccf0f5" height="250"/>
</p>

##### Delete the story

![image](https://github.com/user-attachments/assets/1b3cc777-2b9e-4558-95b0-39e678ed7e00)

This icon permits to delete the story. You have a dialog window in case you misclicked and do not want to delete the issue.

<p align="center">
  <img src="https://github.com/user-attachments/assets/15cfe2b1-51d5-4106-b54e-086ec97736b3" height="250"/>
</p>

##### Modify the story

![image](https://github.com/user-attachments/assets/f522fa85-b973-4394-899c-74af01292a96)

This icon permits to modify the story. You have a dialog window in which you can change the title and the description.

<p align="center">
  <img src="https://github.com/user-attachments/assets/7f60f839-afc9-43a5-9b48-89e7b9c61ac8" height="250"/>
</p>

##### Send the story to vote

![image](https://github.com/user-attachments/assets/8ae38a06-cc97-4ea9-b3a8-9c90deb8ba49)

This icon permits to send the story to vote. By clicking in this button, this story will appear in the room for every participant and everyone has a deck of cards to vote.

<p align="center">
  <img src="https://github.com/user-attachments/assets/021bb9f8-e93b-4bb5-adff-f84030ff77fc" height="250"/>
</p>

##### Save story

![image](https://github.com/user-attachments/assets/f54510d0-5e4f-4731-9a15-e0e08a88eb2f)

This icon permits to save the story. So, it will calculate the final estimate (mean of scores of all participants during the last round). Then, it will also save all the data related to the story into the database so  you can find in the `/history` page. To specify that the story is saved, the icon appears darker. For example, let us suppose that we have two participants. In the last round, one voted 7 and the other 8. The administrator decides to save the issue. Here is the display:

<p align="center">
  <img src="https://github.com/user-attachments/assets/6ccb1615-9832-4e76-8afd-58363642e353" height="250"/>
</p>

 #### New Round : Administrator only

 ![image](https://github.com/user-attachments/assets/d7733fb5-7cdc-4093-a723-2a6b566cb901)

 This icon is to start a new round for a planning poker estimation. So the participants can submit a new estimation for this new round.
 
 #### Reset estimation

![image](https://github.com/user-attachments/assets/57f1aab1-ef8b-445f-b2ab-f69bcda484e9)

 This icon is to reset your estimation (just for you), so it does not create another round.
 
 #### Quit room

 ![image](https://github.com/user-attachments/assets/1dd53202-f7fe-44eb-84d5-e24209882540)

 This icon is to quit the room.
 
 #### Share room

<p align="center">
  <img src="https://github.com/user-attachments/assets/4b14b3a6-a6d0-45cf-8796-381efe9afc6b" height="250"/>
</p>

 This icon is to share the room. You can share

 - The room ID
 - The room link
 - The room link by a QR-code

## History : only for logged in administrators

![](https://github.com/user-attachments/assets/9652969d-6564-4cf7-bd68-df92687ce1ef3)

This is in the `/history` page. It permits to have some statistics for every story. The main display is composed of :

- The story title
- The story description
- The final estimate (mean of score of all participants for the last round)
- The date (DD/MM/YYYY HH:mm:ss) the story has been saved
- The charts icon for more detailed statistics

Here is how it looks like when you click on the charts icon:

![](https://github.com/user-attachments/assets/cb4d564b-a3eb-41b6-8c12-27fb7e964be3)

As you can see, you have :

- For the general display
   - Story title
   - Story description
   - Participants' name
   - Number of rounds
   - If the final round leads to a convergence (everyone voted the same score), or not
 
- For the first/last round (to see the evolution of the scores)
   - A bar chart giving the repartition of the scores   
   - The mean
   - The median
   - The standard deviation
 
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
   docker compose up --watch --build
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

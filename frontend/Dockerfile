FROM node:23.11.0-alpine

WORKDIR /usr/src/app

COPY . .

EXPOSE 5173

RUN npm install

CMD ["npm", "run", "dev"]

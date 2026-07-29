FROM node:16

RUN apt update && apt install -y apt-transport-https ca-certificates sqlite3
WORKDIR /app

COPY package*.json ./

RUN npm install
RUN npm uninstall --save sqlite3 
RUN npm install sqlite3 --save

COPY . .

EXPOSE 3000
CMD [ "node", "src/index.js" ]
# ENTRYPOINT ["tail"]
# CMD ["-f","/dev/null"]

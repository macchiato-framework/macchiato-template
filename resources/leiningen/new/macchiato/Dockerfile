FROM node:latest

# Create app directory
RUN mkdir -p /usr/src/{{name}}
WORKDIR /usr/src/{{name}}

# Install app dependencies
COPY package.json /usr/src/{{name}}
RUN npm install pm2 -g
RUN npm install

# Bundle app source
COPY target/release/{{name}}.js /usr/src/{{name}}
COPY public /usr/src/{{name}}/public

ENV HOST 0.0.0.0

EXPOSE 3000
CMD [ "pm2-docker", "/usr/src/{{name}}/{{name}}.js" ]
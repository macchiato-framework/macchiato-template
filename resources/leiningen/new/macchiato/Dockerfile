FROM mhart/alpine-node:latest

MAINTAINER Your Name <you@example.com>

# Create app directory
RUN mkdir -p /{{name}}
WORKDIR /{{name}}

# Install app dependencies
COPY package.json /{{name}}
RUN npm install pm2 -g
RUN npm install

# Bundle app source
COPY target/release/{{name}}.js /{{name}}/{{name}}.js
COPY public /{{name}}/public

ENV HOST 0.0.0.0

EXPOSE 3000
CMD [ "pm2-docker", "/{{name}}/{{name}}.js" ]

# Use a Node.js base image for building the Angular app
FROM node:18-alpine as build

WORKDIR /app

COPY . .

RUN npm install --force
RUN npm run build --prod

# Use a web server to serve the frontend (e.g., Nginx)
FROM nginx:alpine

# Copy the build output to the Nginx html directory
COPY --from=build /app/dist/socialmedia-fe /usr/share/nginx/html

# Expose the port that Nginx will run on
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]

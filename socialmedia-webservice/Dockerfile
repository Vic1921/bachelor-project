# Use a base image with JDK
FROM eclipse-temurin:21-jre-alpine

# Install dependencies for running Chrome in headless mode
RUN apk --no-cache add \
    curl \
    unzip \
    bash \
    fontconfig \
    freetype \
    ttf-dejavu \
    libstdc++ \
    chromium \
    chromium-chromedriver \
    dumb-init

# Set environment variables for Chrome and WebDriver
ENV CHROME_BIN=/usr/bin/chromium-browser \
    CHROME_DRIVER=/usr/bin/chromedriver

# Set the working directory inside the container
WORKDIR /app
RUN ls -la
# Copy the built jar file into the container
COPY target/socialmedia-webservice-0.0.1-SNAPSHOT.jar /app/socialmedia-webservice.jar

# Set environment variables
ENV JAVA_OPTS=""
ENV SPRING_DATASOURCE_URL=jdbc:h2:file:/data/demo
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
#ENV WEB_DRIVER_PATH="/usr/bin/chromedriver"

# Run the jar file
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar socialmedia-webservice.jar"]

# Expose the port that the Spring Boot application runs on
EXPOSE 8081

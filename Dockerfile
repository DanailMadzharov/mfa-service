FROM amazoncorretto:17 AS build
ENV APP_DIR=/app
WORKDIR $APP_DIR

COPY . .

COPY ./*gradle* $APP_DIR/
COPY gradle $APP_DIR/gradle
# Build the project and the JAR file
RUN ./gradlew clean build -x test

FROM amazoncorretto:17
ENV APP_DIR=/app
WORKDIR $APP_DIR
COPY --from=build $APP_DIR/build/libs/*.jar $APP_DIR/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
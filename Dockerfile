FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle .
COPY gradlew .
COPY settings.gradle .
COPY src src

RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/basic-auth.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]